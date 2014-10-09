package com.login.android.cardapio.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.login.android.cardapio.HomeActivity;
import com.login.android.cardapio.R;
import com.login.android.cardapio.adapter.GridItensItemCardapioAdapter;
import com.login.android.cardapio.adapter.PublicidadeFragmentAdapter;
import com.login.android.cardapio.business.BusinessResult;
import com.login.android.cardapio.business.PublicidadeBS;
import com.login.android.cardapio.model.CatagoriaCardapio;
import com.login.android.cardapio.model.CategoriaCardapioItemSys;
import com.login.android.cardapio.model.Publicidade;
import com.login.android.cardapio.model.ServerResponse;
import com.login.android.cardapio.util.Constantes;
import com.login.android.cardapio.view.ActionBar;
import com.viewpagerindicator.PageIndicator;

public class CardapioFragment extends Fragment implements BusinessResult {

	private View view;
	private HomeActivity activity;
	private List<Publicidade> listPublicidade = null;
	private ViewPager viewPagerMidia = null;
	private PageIndicator mIndicator = null;
	private PublicidadeFragmentAdapter publicidadeFragmentAdapter = null;
	private GridView gridViewMenu;
	private GridItensItemCardapioAdapter gridItensItemMenuAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.view = inflater.inflate(R.layout.fragment_cardapio, container, false);
		((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);

		this.viewPagerMidia = (ViewPager) this.view.findViewById(R.id.fragment_cardapio_view_pager_midia);
		this.mIndicator = (PageIndicator) this.view.findViewById(R.id.fragment_cardapio_view_pager_midia_indicator);
		this.gridViewMenu = (GridView) this.view.findViewById(R.id.fragment_cardapio_grid_view_menu);

		new PublicidadeBS(this).getPublicidade();

		this.gridItensItemMenuAdapter = new GridItensItemCardapioAdapter(this.activity, this.activity.getListaItemCardapio(), new CatagoriaCardapio());
		this.gridViewMenu.setAdapter(gridItensItemMenuAdapter);

		this.gridViewMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				ImageView imageView = (ImageView) v.findViewById(R.id.grid_view_item_menu_image_view);
				CategoriaCardapioItemSys itemMenu = (CategoriaCardapioItemSys) imageView.getTag();

				if (ListaItemCardapioFragment.class.equals(itemMenu.getClasse())) {
					goToFragmentListaItemCardapio(itemMenu);
				} else if (ListaTodosItemCardapioFragment.class.equals(itemMenu.getClasse())) {
					goToFragmentListaTodosItemCardapio(itemMenu);
				}
			}
		});

		return view;
	}

	private void goToFragmentListaTodosItemCardapio(CategoriaCardapioItemSys itemMenu) {

		ListaTodosItemCardapioFragment newFragment = new ListaTodosItemCardapioFragment();
		Bundle args = new Bundle();
		args.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, itemMenu);
		newFragment.setArguments(args);
		FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.activity_home_fragment_layout, newFragment);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	public void goToFragmentListaItemCardapio(CategoriaCardapioItemSys itemMenu) {
		// If the frag is not available, we're in the one-pane layout and must
		// swap frags...

		// Create fragment and give it an argument for the selected article
		ListaItemCardapioFragment newFragment = new ListaItemCardapioFragment();
		Bundle args = new Bundle();
		args.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, itemMenu);
		newFragment.setArguments(args);
		FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack so the user can navigate
		// back
		transaction.replace(R.id.activity_home_fragment_layout, newFragment);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (HomeActivity) activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;
		this.listPublicidade = (List<Publicidade>) serverResponse.getReturnObject();
		this.publicidadeFragmentAdapter = new PublicidadeFragmentAdapter(this.activity, this.getChildFragmentManager(), this.listPublicidade);
		this.viewPagerMidia.setAdapter(this.publicidadeFragmentAdapter);
		this.mIndicator.setViewPager(this.viewPagerMidia);

	}
}
