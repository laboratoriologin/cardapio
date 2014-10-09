package com.login.beachstop.android.garcom.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.android.garcom.R;
import com.login.beachstop.android.garcom.model.ItemCardapioSubItem;
import com.login.beachstop.android.garcom.quickscroll.Scrollable;

public class PedidoListViewAdapter extends BaseAdapter implements Scrollable {

	private LayoutInflater mInflater;
	private List<ItemCardapioSubItem> listItemCardapioSubItem;

	public PedidoListViewAdapter(final List<ItemCardapioSubItem> list, Context _context) {
		this.listItemCardapioSubItem = list;
		this.mInflater = LayoutInflater.from(_context);
	}

	@Override
	public ItemCardapioSubItem getItem(int position) {
		return this.listItemCardapioSubItem.get(position);
	}

	@Override
	public int getCount() {
		return this.listItemCardapioSubItem.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ItemCardapioSubItem itemCardapioSubItem = this.listItemCardapioSubItem.get(position);
		convertView = mInflater.inflate(R.layout.adapter_list_view_activity_pedido, null);

		ImageView imgEsquerda = (ImageView) convertView.findViewById(R.id.adapter_list_view_activity_imageview_menos);
		ImageView imgDireita = (ImageView) convertView.findViewById(R.id.adapter_list_view_activity_imageview_mais);
		final TextView txtQtd = (TextView) convertView.findViewById(R.id.adapter_list_view_activity_textview_qtd);

		((TextView) convertView.findViewById(R.id.adapter_list_view_activity_pedido_textview_descricao)).setText(itemCardapioSubItem.getItemCardapio().getNome() + " - " + itemCardapioSubItem.getDescricao() + " (" + itemCardapioSubItem.getCodSubItem() + ")");

		txtQtd.setText((String.format("%02d", itemCardapioSubItem.getQtdSelecionado())));

		imgEsquerda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atualizaQtdPedido(false, txtQtd, itemCardapioSubItem);
			}
		});

		imgDireita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atualizaQtdPedido(true, txtQtd, itemCardapioSubItem);
			}
		});

		return convertView;
	}

	@Override
	public String getIndicatorForPosition(int arg0, int arg1) {
		return this.listItemCardapioSubItem.get(arg0).getIndicator();
	}

	@Override
	public int getScrollPosition(int arg0, int arg1) {
		return arg0;
	}

	/**
	 * @return the listItemCardapio
	 */
	public List<ItemCardapioSubItem> getListItemCardapioSubItem() {
		return listItemCardapioSubItem;
	}

	/**
	 * @param listItemCardapio
	 *            the listItemCardapio to set
	 */
	public void setListItemCardapioSubItem(List<ItemCardapioSubItem> listItemCardapioSubItem) {
		this.listItemCardapioSubItem = listItemCardapioSubItem;
	}

	public void zerarQuantidade() {
		for (ItemCardapioSubItem item : this.listItemCardapioSubItem) {
			item.setQtdSelecionado(0l);
		}
		Collections.sort(this.listItemCardapioSubItem);
		this.notifyDataSetChanged();
	}

	public void atualizaQtdPedido(Boolean isSoma, TextView campoQtd, ItemCardapioSubItem itemCardapioSubItem) {

		if (isSoma) {
			if (itemCardapioSubItem.getQtdSelecionado() < 99) {

				try {
					itemCardapioSubItem.addQtd(1l);
					campoQtd.setText((String.format("%02d", itemCardapioSubItem.getQtdSelecionado())));
				} catch (Exception e) {
					// TODO: EXIBIR MSG DE ERRO
				}
			}

		} else {

			if (itemCardapioSubItem.getQtdSelecionado() > 0) {

				try {
					itemCardapioSubItem.subQtd(1l);
					campoQtd.setText((String.format("%02d", itemCardapioSubItem.getQtdSelecionado())));
				} catch (Exception e) {
					// TODO: EXIBIR MSG DE ERRO
				}
			}
		}
	}
}
