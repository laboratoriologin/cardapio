package com.login.beachstop.android.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.image.ImageFetcher;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class GridItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Item> itens;
    private ImageFetcher mImageFetcher;

    public GridItemAdapter(Context context, List<Item> itens, ImageFetcher imageFetcher) {

        this.itens = itens;
        this.mInflater = LayoutInflater.from(context);
        this.mImageFetcher = imageFetcher;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = itens.get(position);

        convertView = mInflater.inflate(R.layout.adapter_grid_view_item, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.adapter_grid_view_item_image_view);

        if (item.getImagem().length() != 0) {

            this.mImageFetcher.loadImage(Constantes.URL_IMG + item.getImagem(), imageView);

        }

        ((TextView) convertView.findViewById(R.id.adapter_grid_view_item_text_view_nome)).setText(item.getNome());
        ((TextView) convertView.findViewById(R.id.adapter_grid_view_item_text_view_descricao)).setText(item.getDescricao());

        return convertView;
    }

    @Override
    public int getCount() {

        return itens.size();

    }

    @Override
    public Object getItem(int position) {

        return itens.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
