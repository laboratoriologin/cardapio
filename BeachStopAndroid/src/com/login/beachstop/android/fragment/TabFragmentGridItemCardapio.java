package com.login.beachstop.android.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.adapter.GridItensItemCategoriaCardapioAdapter;
import com.login.beachstop.android.model.CategoriaCardapioItemSys;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.util.image.ImageCache.ImageCacheParams;
import com.login.beachstop.android.util.image.ImageFetcher;

public class TabFragmentGridItemCardapio extends Fragment {

	private List<ItemCardapio> listItemCardapio;
	private View view;
	private HomeActivity homeActivity;
	private CategoriaCardapioItemSys itemMenu;
	private GridItensItemCategoriaCardapioAdapter gridItensItemCategoriaCardapioAdapter;
	private GridView gridView;
	private ImageFetcher mImageFetcher;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		ImageCacheParams mImageCacheParams = new ImageCacheParams(this.getActivity().getBaseContext(), Constantes.IMAGE_CACHE);
		mImageCacheParams.setMemCacheSizePercent(0.50f);

		this.mImageFetcher = new ImageFetcher(this.getActivity().getBaseContext(), 500);
		this.mImageFetcher.setLoadingImage(R.drawable.placeholder);
		this.mImageFetcher.addImageCache(this.getActivity(), Constantes.IMAGE_CACHE);

		this.itemMenu = (CategoriaCardapioItemSys) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
		this.homeActivity = (HomeActivity) inflater.getContext();
		this.listItemCardapio = this.homeActivity.getDataManager().getAll((long) this.itemMenu.getId());

		this.view = inflater.inflate(R.layout.tab_fragment_grid_item_cardapio, container, false);

		this.gridView = (GridView) this.view.findViewById(R.id.tab_fragment_grid_view_item_cardapio);
		this.gridItensItemCategoriaCardapioAdapter = new GridItensItemCategoriaCardapioAdapter(this.view.getContext(), listItemCardapio, this.mImageFetcher);
		this.gridView.setAdapter(this.gridItensItemCategoriaCardapioAdapter);

		this.gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

				ItemCardapio itemCardapio = listItemCardapio.get(posicaoClicada);

				detailItem(itemCardapio);

			}
		});

		this.gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int scrollState) {
				// Pause fetcher to ensure smoother scrolling when flinging
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
					mImageFetcher.setPauseWork(true);
				} else {
					mImageFetcher.setPauseWork(false);
				}
			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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
		FragmentTransaction transaction = homeActivity.getSupportFragmentManager().beginTransaction();

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
		mImageFetcher.setExitTasksEarly(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setPauseWork(false);
		mImageFetcher.setExitTasksEarly(true);
		mImageFetcher.flushCache();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.closeCache();
	}

}
