package com.login.beachstop.android.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.login.beachstop.android.DefaultActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.utils.Constantes;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class CategoriaGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Categoria> categorias;
    private DisplayImageOptions options;

    public CategoriaGridAdapter(DefaultActivity context, List<Categoria> categorias) {

        this.mInflater = LayoutInflater.from(context);
        this.categorias = categorias;

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

        convertView = mInflater.inflate(R.layout.adapter_grid_view_categoria, null);
        Categoria categoria = categorias.get(position);

        ImageView imgViewItem = (ImageView) convertView.findViewById(R.id.adapter_grid_view_categoria_image_view);

        if (Constantes.TipoCategoriaCardapio.ITEM == categoria.getTipoCategoria()) {

            ImageLoader.getInstance().displayImage(Constantes.URL_IMG + categoria.getImagem(), imgViewItem, options);

//            Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + categoria.getImagem());
//
//            if (img == null) {
//
//                new LoadImage(imgViewItem, convertView.getContext()).execute(Constantes.URL_IMG + categoria.getImagem());
//
//            } else {
//
//                imgViewItem.setImageDrawable(img);
//
//            }
        } else if (Constantes.TipoCategoriaCardapio.KIT == categoria.getTipoCategoria()) {

            imgViewItem.setImageResource(categoria.getResourceImg());

        } else {

            imgViewItem.setImageResource(categoria.getResourceImg());

        }

        imgViewItem.setTag(categoria);

        return convertView;
    }

    @Override
    public int getCount() {

        return this.categorias.size();

    }

    @Override
    public Object getItem(int position) {

        return this.categorias.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

}