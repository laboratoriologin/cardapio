package com.login.android.cardapio.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.android.cardapio.R;
import com.login.android.cardapio.model.ItemCardapio;
import com.login.android.cardapio.util.Constantes;
import com.login.android.cardapio.util.DrawableManager;
import com.login.android.cardapio.util.LoadImage;

public class GridItensItemCategoriaCardapioAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ItemCardapio> listaItemCategoriaCardapio;

	public GridItensItemCategoriaCardapioAdapter(Context _context, List<ItemCardapio> _listaItemCategoriaCardapio) {
		this.listaItemCategoriaCardapio = _listaItemCategoriaCardapio;
		this.mInflater = LayoutInflater.from(_context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ItemCardapio itemCardapio = listaItemCategoriaCardapio.get(position);

		convertView = mInflater.inflate(R.layout.fragment_grid_view_item_categoria_cardapio, null);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_image_view);

		Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + itemCardapio.getImagem());

		if (img == null) {

			new LoadImage(imageView, convertView.getContext()).execute(Constantes.URL_IMG + itemCardapio.getImagem());

		} else {

			imageView.setImageDrawable(img);

		}

		((TextView) convertView.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_text_view_nome)).setText(itemCardapio.getNome());
		((TextView) convertView.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_text_view_descricao)).setText(itemCardapio.getGuarnicoes());

		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
}
