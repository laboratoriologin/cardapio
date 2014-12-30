package com.login.beachstop.android.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.util.Constantes;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class GridItensItemCategoriaCardapioAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ItemCardapio> listaItemCategoriaCardapio;
    private DisplayImageOptions options;

    public GridItensItemCategoriaCardapioAdapter(Context _context, List<ItemCardapio> _listaItemCategoriaCardapio) {
        this.listaItemCategoriaCardapio = _listaItemCategoriaCardapio;
        this.mInflater = LayoutInflater.from(_context);

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

        ItemCardapio itemCardapio = listaItemCategoriaCardapio.get(position);

        if (convertView == null) {

            view = mInflater.inflate(R.layout.fragment_grid_view_item_categoria_cardapio, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_image_view);
            imageView.setImageResource(R.drawable.placeholder);
            imageView.setTag(Constantes.URL_IMG + itemCardapio.getImagem());

            viewHolder = new ViewHolder();
            viewHolder.imageView = imageView;
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.imageView.setImageResource(R.drawable.placeholder);
        }


        if (!TextUtils.isEmpty(itemCardapio.getImagem())) {

            ImageLoader.getInstance().displayImage(Constantes.URL_IMG + itemCardapio.getImagem(), viewHolder.imageView, options);

        }else{

            ImageLoader.getInstance().displayImage("", viewHolder.imageView, options);

        }


        ((TextView) view.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_text_view_nome)).setText(itemCardapio.getNome());

        ((TextView) view.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_text_view_descricao)).setText(itemCardapio.getGuarnicoes());

        return view;
    }

    @Override
    public int getCount() {
        return listaItemCategoriaCardapio.size();
    }

    @Override
    public Object getItem(int position) {
        return listaItemCategoriaCardapio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
