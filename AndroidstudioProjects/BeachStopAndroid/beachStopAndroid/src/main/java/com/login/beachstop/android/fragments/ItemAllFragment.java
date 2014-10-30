package com.login.beachstop.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.ItemRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.actionbar.ActionBar;
import com.login.beachstop.android.views.adapters.CategoriaItemExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemAllFragment extends Fragment {

    private CardapioActivity cardapioActivity;
    private View view;
    private ImageView imageViewTopoActionBar;
    private ExpandableListView expandableListView;
    private CategoriaItemExpandableAdapter categoriaItemExpandableAdapter;
    private List<Categoria> categorias;
    private Categoria categoria = new Categoria();
    private ProgressBar progressbar;
    private ResponseListener responseListener = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        for (Item item : (List<Item>) serverResponse.getReturnObject()) {

                            cardapioActivity.getDataManager().getItemDAO().save(item);

                        }
                        startExpamdableList();


                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(cardapioActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(cardapioActivity, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                Toast.makeText(cardapioActivity, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_item_all, container, false);

        ((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
        this.imageViewTopoActionBar = (ImageView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar);

        this.imageViewTopoActionBar.setBackgroundResource(R.drawable.icone_topo_todos_pratos);
        ((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Todos");

        this.expandableListView = (ExpandableListView) this.view.findViewById(R.id.fragment_item_all_expandable_view);
        this.expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Categoria categoriaSelecionada = categorias.get(groupPosition);
                List<Item> itens = cardapioActivity.getDataManager().getItemDAO().getAll(categoriaSelecionada.getId());

                Item item = itens.get(childPosition);

                ItemDetalheFragment newFragment = new ItemDetalheFragment();
                Bundle args = new Bundle();
                args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, item);
                newFragment.setArguments(args);
                FragmentTransaction transaction = cardapioActivity.getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.activity_cardapio_fragment_layout, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();

                return false;
            }
        });

        this.progressbar = (ProgressBar) this.view.findViewById(R.id.fragment_item_all_progress_bar);

        getItemMenuCardapio();

        return view;
    }

    private void setVisibility(Boolean a) {
        if (a) {
            this.progressbar.setVisibility(ProgressBar.GONE);
            this.expandableListView.setVisibility(ProgressBar.VISIBLE);
        } else {
            this.progressbar.setVisibility(ProgressBar.VISIBLE);
            this.expandableListView.setVisibility(ProgressBar.GONE);
        }
    }

    public void getItemMenuCardapio() {

        //TODO: tem que trazer também todos os kits do restaurante

        List<Categoria> categoriaList = this.cardapioActivity.getDataManager().getCategoriaDAO().getAll().subList(0, (this.cardapioActivity.getDataManager().getCategoriaDAO().getQtdCategoria() - 1));
        this.categorias = new ArrayList<Categoria>();

        List<Item> items;
        List<Long> itensIdBusca = new ArrayList<Long>();

        for (Categoria item : categoriaList) {

            this.categorias.add(item);

            items = this.cardapioActivity.getDataManager().getItemDAO().getAll(item.getId());

            if (items.size() == 0)
                itensIdBusca.add(item.getId());

        }

        if (itensIdBusca.size() != 0)

            new ItemRequest(responseListener).getItemByCategorias(itensIdBusca);
        else

            startExpamdableList();
    }

    private void startExpamdableList() {
        this.categoriaItemExpandableAdapter = new CategoriaItemExpandableAdapter(this.cardapioActivity, this.categorias);
        this.expandableListView.setAdapter(this.categoriaItemExpandableAdapter);
        setVisibility(true);
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        this.cardapioActivity = (CardapioActivity) activity;
        Bundle args = getArguments();

    }

}
