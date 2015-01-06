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
import com.login.beachstop.android.models.Kit;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.adapters.KitListAdapter;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class KitListFragment extends Fragment {

    private List<Kit> kits;
    private View view;
    private Categoria categoria;
    private KitListAdapter kitListAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.categoria = (Categoria) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
        this.kits = ((CardapioActivity) getActivity()).getDataManager().getKitDAO().getAll();
        this.kitListAdapter = new KitListAdapter(this.view.getContext(), kits);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        this.view = inflater.inflate(R.layout.fragment_item_list, container, false);
        this.listView = (ListView) this.view.findViewById(R.id.fragment_item_list_list_view);
        this.listView.setAdapter(this.kitListAdapter);

        this.listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

                Kit kit = kits.get(posicaoClicada);
                ItemDetalheFragment newFragment = new ItemDetalheFragment();
                Bundle args = new Bundle();

                args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, kit);
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
        kitListAdapter.notifyDataSetChanged();
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
