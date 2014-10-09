package com.login.android.cardapio.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.login.android.cardapio.DefaultActivity;
import com.login.android.cardapio.R;
import com.login.android.cardapio.model.CatagoriaCardapio;
import com.login.android.cardapio.model.CategoriaCardapioItem;
import com.login.android.cardapio.model.CategoriaCardapioItemSys;

public class GridItensItemCardapioAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<CategoriaCardapioItem> listaItemMenu;
	private CatagoriaCardapio menu;

	public GridItensItemCardapioAdapter(DefaultActivity _context, List<CategoriaCardapioItem> _listaItemMenu, CatagoriaCardapio _menu) {
		this.listaItemMenu = _listaItemMenu;
		this.mInflater = LayoutInflater.from(_context);
		this.menu = _menu;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CategoriaCardapioItemSys itemMenu = this.menu.getItemMenu(listaItemMenu.get(position).getId());
		convertView = mInflater.inflate(R.layout.fragment_grid_view_item_cardapio, null);
		ImageView imgViewItem = (ImageView) convertView.findViewById(R.id.grid_view_item_menu_image_view);
		imgViewItem.setBackgroundResource(itemMenu.getResourceImg());
		imgViewItem.setTag(itemMenu);

		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listaItemMenu.size();
	}

	@Override
	public Object getItem(int position) {
		return listaItemMenu.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
