package com.login.beachstop.android.views.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.utils.Constantes;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Item> itens;
    private DisplayImageOptions options;

    public ItemGridAdapter(Context context, List<Item> itens) {

        this.itens = itens;
        this.mInflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .delayBeforeLoading(100)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder viewHolder;

        Item item = itens.get(position);

        if (convertView == null) {

            view = mInflater.inflate(R.layout.adapter_grid_view_item, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.adapter_grid_view_item_image_view);
            imageView.setImageResource(R.drawable.placeholder);
            imageView.setTag(Constantes.URL_IMG + item.getImagem());

            viewHolder = new ViewHolder();
            viewHolder.imageView = imageView;
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.imageView.setImageResource(R.drawable.placeholder);
        }


        if (!TextUtils.isEmpty(item.getImagem())) {

            ImageLoader.getInstance().displayImage(Constantes.URL_IMG + item.getImagem(), viewHolder.imageView, options);

        } else {

            ImageLoader.getInstance().displayImage("", viewHolder.imageView, options);

        }


        ((TextView) convertView.findViewById(R.id.adapter_grid_view_item_text_view_nome)).setText(item.getNome());
        ((TextView) convertView.findViewById(R.id.adapter_grid_view_item_text_view_descricao)).setText(item.getDescricao());

        return view;
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

    static class ViewHolder {
        ImageView imageView;
    }
}
