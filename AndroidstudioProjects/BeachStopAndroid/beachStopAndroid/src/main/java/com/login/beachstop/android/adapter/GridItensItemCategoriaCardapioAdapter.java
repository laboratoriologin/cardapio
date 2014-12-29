package com.login.beachstop.android.adapter;

import java.util.List;

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
import com.login.beachstop.android.util.image.ImageFetcher;

public class GridItensItemCategoriaCardapioAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ItemCardapio> listaItemCategoriaCardapio;
	private ImageFetcher mImageFetcher;

	public GridItensItemCategoriaCardapioAdapter(Context _context, List<ItemCardapio> _listaItemCategoriaCardapio, ImageFetcher _ImageFetcher) {
		this.listaItemCategoriaCardapio = _listaItemCategoriaCardapio;
		this.mInflater = LayoutInflater.from(_context);
		this.mImageFetcher = _ImageFetcher;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ItemCardapio itemCardapio = listaItemCategoriaCardapio.get(position);

		convertView = mInflater.inflate(R.layout.fragment_grid_view_item_categoria_cardapio, null);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_image_view);

		if (!TextUtils.isEmpty(itemCardapio.getImagem())) {

			this.mImageFetcher.loadImage(Constantes.URL_IMG + itemCardapio.getImagem(), imageView);

		}

		((TextView) convertView.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_text_view_nome)).setText(itemCardapio.getNome());

		((TextView) convertView.findViewById(R.id.fragment_grid_view_item_categoria_cardapio_text_view_descricao)).setText(itemCardapio.getGuarnicoes());

		return convertView;
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
}
