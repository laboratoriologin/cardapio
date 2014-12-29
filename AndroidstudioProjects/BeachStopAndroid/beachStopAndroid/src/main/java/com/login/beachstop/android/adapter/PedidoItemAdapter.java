package com.login.beachstop.android.adapter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.fragment.TabFragmentPedido;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.sqlite.dao.DataManager;

public class PedidoItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Pedido pedido;
	private DataManager dataManager;
	private TextView valorTotal;
	private TabFragmentPedido tabFragmentPedido;

	public PedidoItemAdapter(Context _context, Pedido _pedido, DataManager _dataManager, TextView _valorTotal, TabFragmentPedido _tabFragmentPedido) {
		this.setPedido(_pedido);
		this.mInflater = LayoutInflater.from(_context);
		this.dataManager = _dataManager;
		this.valorTotal = _valorTotal;
		this.tabFragmentPedido = _tabFragmentPedido;
	}

	public int getCount() {
		return this.getPedido().getListPedidoItem().size();
	}

	@Override
	public Object getItem(int arg0) {
		return this.getPedido().getListPedidoItem().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.tab_fragment_pedido_item_adapter, null);

		final PedidoItem pedidoItem = this.getPedido().getListPedidoItem().get(position);
		ItemCardapio itemCardapio = this.dataManager.getItemCardapioDAO().get(pedidoItem.getSubItem().getIdItemCardapio());

		final TextView tv = (TextView) convertView.findViewById(R.id.tab_fragment_pedido_item_adapeter_text_view_qtd);
		tv.setText((String.format("%02d", pedidoItem.getQuantidade())));
		((TextView) convertView.findViewById(R.id.tab_fragment_pedido_item_adapeter_text_view_descricao)).setText(itemCardapio.getNome() + " - " + pedidoItem.getSubItem().getDescricao());

		convertView.findViewById(R.id.tab_fragment_pedido_item_adapeter_image_view_seta_esquerda).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atualizaQtdPedido(false, tv, pedidoItem);
			}
		});

		convertView.findViewById(R.id.tab_fragment_pedido_item_adapeter_seta_direita).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atualizaQtdPedido(true, tv, pedidoItem);
			}
		});

		convertView.findViewById(R.id.tab_fragment_pedido_item_adapeter_text_view_menos).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteItemPedido(pedidoItem);
			}
		});

		return convertView;
	}

	public void atualizaQtdPedido(Boolean isSoma, TextView campoQtd, PedidoItem pedidoItem) {

		if (isSoma) {
			if (pedidoItem.getQuantidade() < 99) {

				try {
					pedidoItem.addQtd(1l);
					this.dataManager.getPedidoItemDAO().update(pedidoItem, pedidoItem.getId());
					campoQtd.setText((String.format("%02d", pedidoItem.getQuantidade())));
				} catch (Exception e) {
					// TODO: EXIBIR MSG DE ERRO
				}

				atualizaValorTotalPedido();
			}

		} else {

			if (pedidoItem.getQuantidade() > 0) {

				try {
					pedidoItem.subQtd(1l);
					this.dataManager.getPedidoItemDAO().update(pedidoItem, pedidoItem.getId());
					campoQtd.setText((String.format("%02d", pedidoItem.getQuantidade())));
				} catch (Exception e) {
					// TODO: EXIBIR MSG DE ERRO
				}
				atualizaValorTotalPedido();
			}
		}
	}

	public void deleteItemPedido(PedidoItem pedidoItem) {

		if (this.dataManager.getPedidoItemDAO().delete(pedidoItem.getId())) {

			this.pedido.getListPedidoItem().remove(this.pedido.getListPedidoItem().indexOf(pedidoItem));

			this.notifyDataSetChanged();

			atualizaValorTotalPedido();
		}
	}

	public void atualizaValorTotalPedido() {

		BigDecimal valorTotalPedido = new BigDecimal(0);
		for (PedidoItem pedidoItem : this.pedido.getListPedidoItem()) {
			valorTotalPedido = valorTotalPedido.add(new BigDecimal(pedidoItem.getQuantidade()).multiply(pedidoItem.getSubItem().getValorBigDecimal()));
		}

		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		this.valorTotal.setText(format.format(valorTotalPedido.doubleValue()));

		this.tabFragmentPedido.changeViewPedido();
	}

	/**
	 * @return the pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * @param pedido
	 *            the pedido to set
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
