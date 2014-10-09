package com.login.beachstop.android.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.login.beachstop.android.DefaultActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.model.CatagoriaCardapio;
import com.login.beachstop.android.model.CategoriaCardapioItemSys;

public class GridItensItemMenuAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Long> listaItemMenu;
	private CatagoriaCardapio menu;

	public GridItensItemMenuAdapter(DefaultActivity _context, List<Long> _listaItemMenu, CatagoriaCardapio _menu) {
		this.listaItemMenu = _listaItemMenu;
		this.mInflater = LayoutInflater.from(_context);
		this.menu = _menu;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CategoriaCardapioItemSys itemMenu = this.menu.getItemMenu(listaItemMenu.get(position));
		convertView = mInflater.inflate(R.layout.grid_view_item_menu, null);
		ImageView imgViewItem = (ImageView) convertView.findViewById(R.id.grid_view_item_menu_image_view);
		imgViewItem.setImageResource(itemMenu.getResourceImg());
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
