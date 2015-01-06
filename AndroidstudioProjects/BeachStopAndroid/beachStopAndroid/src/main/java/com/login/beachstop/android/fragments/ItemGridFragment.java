package com.login.beachstop.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.adapters.ItemGridAdapter;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemGridFragment extends Fragment {

    private List<Item> itens;
    private View view;
    private Categoria categoria;
    private ItemGridAdapter itemGridAdapter;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.categoria = (Categoria) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
        this.itens = ((CardapioActivity) getActivity()).getDataManager().getItemDAO().getAll(this.categoria.getId());
        this.itemGridAdapter = new ItemGridAdapter(this.view.getContext(), itens);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        this.view = inflater.inflate(R.layout.fragment_item_grid, container, false);
        this.gridView = (GridView) this.view.findViewById(R.id.fragment_item_grid_grid_view);
        this.gridView.setAdapter(this.itemGridAdapter);

        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

                Item item = itens.get(posicaoClicada);
                ItemDetalheFragment newFragment = new ItemDetalheFragment();
                Bundle args = new Bundle();

                args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, item);
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
        itemGridAdapter.notifyDataSetChanged();
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
