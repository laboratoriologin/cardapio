package com.login.android.cardapio.fragment;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.cardapio.HomeActivity;
import com.login.android.cardapio.R;
import com.login.android.cardapio.adapter.ExpandableListViewTodosItensCardapioAdapter;
import com.login.android.cardapio.business.BusinessResult;
import com.login.android.cardapio.business.ItemCardapioBS;
import com.login.android.cardapio.exception.PersistException;
import com.login.android.cardapio.model.CatagoriaCardapio;
import com.login.android.cardapio.model.ItemCardapio;
import com.login.android.cardapio.model.CategoriaCardapioItem;
import com.login.android.cardapio.model.CategoriaCardapioItemSys;
import com.login.android.cardapio.model.ServerResponse;
import com.login.android.cardapio.util.Constantes;
import com.login.android.cardapio.view.ActionBar;

public class ListaTodosItemCardapioFragment extends Fragment implements BusinessResult {

	private HomeActivity homeActivity;
	private View view;
	private ImageView imageViewTopoActionBar;
	private CategoriaCardapioItemSys itemMenu;
	private ExpandableListView expandableListViewTodosItensCardapio;
	private ExpandableListViewTodosItensCardapioAdapter expandableListViewTodosItensCardapioAdapter;
	private List<CategoriaCardapioItemSys> listItemMenu;
	private CatagoriaCardapio menuCategoriaCardapio = new CatagoriaCardapio();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = inflater.inflate(R.layout.fragment_lista_todos_item_cardapio, container, false);

		((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		this.imageViewTopoActionBar = (ImageView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar);
		this.imageViewTopoActionBar.setBackgroundResource(this.itemMenu.getResourceImgTopoCardapio());
		((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText(this.itemMenu.getNome());

		this.expandableListViewTodosItensCardapio = (ExpandableListView) this.view.findViewById(R.id.fragment_lista_todos_item_cardapio_expandable_list_view_todos_item);
		this.expandableListViewTodosItensCardapio.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

				CategoriaCardapioItemSys itemCategoriaSys = listItemMenu.get(groupPosition);
				List<ItemCardapio> listItemCardapio = homeActivity.getDataManager().getAll(itemCategoriaSys.getId());

				ItemCardapio itemCardapio = listItemCardapio.get(childPosition);

				DetalheItemCardapioFragment newFragment = new DetalheItemCardapioFragment();
				Bundle args = new Bundle();
				args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, itemCardapio);
				newFragment.setArguments(args);
				FragmentTransaction transaction = homeActivity.getSupportFragmentManager().beginTransaction();

				transaction.replace(R.id.activity_home_fragment_layout, newFragment);
				transaction.addToBackStack(null);

				transaction.commit();

				return false;
			}
		});

		getItemMenuCardapio();

		return view;
	}

	public void getItemMenuCardapio() {

		List<CategoriaCardapioItem> listIdItemMenu = this.homeActivity.getListaItemCardapio().subList(0, (this.homeActivity.getListaItemCardapio().size() - 1));
		this.listItemMenu = new ArrayList<CategoriaCardapioItemSys>();

		List<ItemCardapio> listItemCardapio;
		List<Long> listItemBuscarSys = new ArrayList<Long>();

		for (CategoriaCardapioItem item : listIdItemMenu) {
			this.listItemMenu.add(this.menuCategoriaCardapio.getItemMenu(item.getId()));
			listItemCardapio = this.homeActivity.getDataManager().getAll(item.getId());
			if (listItemCardapio.size() == 0)
				listItemBuscarSys.add(item.getId());
		}

		if (listItemBuscarSys.size() != 0)
			new ItemCardapioBS(this).getCardapioEmpresa(listItemBuscarSys);
		else
			startExpamdableList();
	}

	private void startExpamdableList() {
		this.expandableListViewTodosItensCardapioAdapter = new ExpandableListViewTodosItensCardapioAdapter(this.homeActivity, this.listItemMenu);
		this.expandableListViewTodosItensCardapio.setAdapter(this.expandableListViewTodosItensCardapioAdapter);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.setHomeActivity((HomeActivity) activity);
		Bundle args = getArguments();
		this.itemMenu = (CategoriaCardapioItemSys) args.getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
	}

	public HomeActivity getHomeActivity() {
		return homeActivity;
	}

	public void setHomeActivity(HomeActivity homeActivity) {
		this.homeActivity = homeActivity;
	}

	@SuppressWarnings("unchecked")
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {
			try {
				for (ItemCardapio itemCardapio : (List<ItemCardapio>) serverResponse.getReturnObject()) {
					this.homeActivity.getDataManager().save(itemCardapio);
				}
				startExpamdableList();

			} catch (PersistException e) {
				Toast.makeText(homeActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(homeActivity, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
		}

	}
}