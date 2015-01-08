package com.login.beachstop.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.Kit;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.adapters.ItemListAdapter;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemListFragment extends Fragment {

    private List<?> objects;
    private View view;
    private Categoria categoria;
    private ItemListAdapter itemListAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.categoria = (Categoria) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);

        if(Constantes.TipoCategoriaCardapio.ITEM.equals(this.categoria.getTipoCategoria()))
            this.objects = ((CardapioActivity) getActivity()).getDataManager().getItemDAO().getAll(this.categoria.getId());
        else
            this.objects = ((CardapioActivity) getActivity()).getDataManager().getKitDAO().getAll();

        this.itemListAdapter = new ItemListAdapter(getActivity(), this.objects);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        this.view = inflater.inflate(R.layout.fragment_item_list, container, false);
        this.listView = (ListView) this.view.findViewById(R.id.fragment_item_list_list_view);
        this.listView.setAdapter(this.itemListAdapter);

        this.listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

                Object obj = objects.get(posicaoClicada);
                Fragment newFragment;
                Bundle args = new Bundle();

                if (obj instanceof Item) {
                    newFragment = new ItemDetalheFragment();
                    args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, (Item) obj);
                } else {
                    newFragment = new KitDetalheFragment();
                    args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, (Kit) obj);
                }

                newFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_cardapio_fragment_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
