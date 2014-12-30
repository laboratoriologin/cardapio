package com.login.beachstop.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.adapter.GridItensItemCategoriaCardapioAdapter;
import com.login.beachstop.android.model.CategoriaCardapioItemSys;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.util.Constantes;

import java.util.List;

public class TabFragmentGridItemCardapio extends Fragment {

    private List<ItemCardapio> listItemCardapio;
    private View view;
    private CategoriaCardapioItemSys itemMenu;
    private GridItensItemCategoriaCardapioAdapter gridItensItemCategoriaCardapioAdapter;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.itemMenu = (CategoriaCardapioItemSys) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
        this.listItemCardapio = ((HomeActivity) getActivity()).getDataManager().getAll((long) this.itemMenu.getId());

        this.gridItensItemCategoriaCardapioAdapter = new GridItensItemCategoriaCardapioAdapter(getActivity(), listItemCardapio);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }


        this.view = inflater.inflate(R.layout.tab_fragment_grid_item_cardapio, container, false);

        this.gridView = (GridView) this.view.findViewById(R.id.tab_fragment_grid_view_item_cardapio);

        this.gridView.setAdapter(this.gridItensItemCategoriaCardapioAdapter);

        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

                ItemCardapio itemCardapio = listItemCardapio.get(posicaoClicada);

                detailItem(itemCardapio);

            }
        });

        if (this.listItemCardapio.size() == 1) {

            this.detailItem(this.listItemCardapio.get(0));

        }

        return view;
    }

    private void detailItem(ItemCardapio itemCardapio) {

        DetalheItemCardapioFragment newFragment = new DetalheItemCardapioFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, itemCardapio);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack so the user can
        // navigate
        // back
        transaction.add(R.id.activity_home_fragment_layout, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        gridItensItemCategoriaCardapioAdapter.notifyDataSetChanged();
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
