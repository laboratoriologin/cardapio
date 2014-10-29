package com.login.beachstop.android.fragments;

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

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.image.ImageCache.ImageCacheParams;
import com.login.beachstop.android.utils.image.ImageFetcher;
import com.login.beachstop.android.views.adapters.GridItemAdapter;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemGridFragment extends Fragment {

    private List<Item> itens;
    private View view;
    private CardapioActivity cardapioActivity;
    private Categoria categoria;
    private GridItemAdapter gridItemAdapter;
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

        this.categoria = (Categoria) getArguments().getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
        this.cardapioActivity = (CardapioActivity) inflater.getContext();
        this.itens = this.cardapioActivity.getDataManager().getItemDAO().getAll(this.categoria.getId());

        this.view = inflater.inflate(R.layout.fragment_item_grid, container, false);

        this.gridView = (GridView) this.view.findViewById(R.id.fragment_item_grid_grid_view);

        this.gridItemAdapter = new GridItemAdapter(this.view.getContext(), itens, this.mImageFetcher);
        this.gridView.setAdapter(this.gridItemAdapter);

        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

                Item item = itens.get(posicaoClicada);

                ItemDetalheFragment newFragment = new ItemDetalheFragment();
                Bundle args = new Bundle();
                args.putSerializable(Constantes.ARG_ITEM_CARDAPIO, item);
                newFragment.setArguments(args);
                FragmentTransaction transaction = cardapioActivity.getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.activity_cardapio_fragment_layout, newFragment);
                transaction.addToBackStack(null);


                transaction.commit();
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

        return view;
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
