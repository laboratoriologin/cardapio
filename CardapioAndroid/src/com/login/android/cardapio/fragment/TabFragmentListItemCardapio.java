package com.login.android.cardapio.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.login.android.cardapio.HomeActivity;
import com.login.android.cardapio.R;
import com.login.android.cardapio.adapter.ListItensItemCategoriaCardapioAdapter;
import com.login.android.cardapio.model.ItemCardapio;
import com.login.android.cardapio.model.CategoriaCardapioItemSys;
import com.login.android.cardapio.util.Constantes;

public class TabFragmentListItemCardapio extends Fragment {

	private List<ItemCardapio> listItemCardapio;
	private View view;
	private HomeActivity homeActivity;
	private CategoriaCardapioItemSys itemMenu;
	private ListItensItemCategoriaCardapioAdapter listItensItemCategoriaCardapioAdapter;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		this.itemMenu = (CategoriaCardapioItemSys) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
		this.homeActivity = (HomeActivity) inflater.getContext();
		this.listItemCardapio = this.homeActivity.getDataManager().getAll((long) this.itemMenu.getId());

		this.view = inflater.inflate(R.layout.tab_fragment_list_item_cardapio, container, false);

		this.listView = (ListView) this.view.findViewById(R.id.tab_fragment_list_view_item_cardapio);
		this.listItensItemCategoriaCardapioAdapter = new ListItensItemCategoriaCardapioAdapter(this.view.getContext(), listItemCardapio);
		this.listView.setAdapter(this.listItensItemCategoriaCardapioAdapter);
		
		this.listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {
				
				ItemCardapio itemCardapio = listItemCardapio.get(posicaoClicada);
				
				DetalheItemCardapioFragment newFragment = new DetalheItemCardapioFragment();
				Bundle args = new Bundle();
				args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, itemCardapio);
				newFragment.setArguments(args);
				FragmentTransaction transaction = homeActivity.getSupportFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this
				// fragment,
				// and add the transaction to the back stack so the user can navigate
				// back
				transaction.replace(R.id.activity_home_fragment_layout, newFragment);
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();

			}
		});

		return view;
	}
}
