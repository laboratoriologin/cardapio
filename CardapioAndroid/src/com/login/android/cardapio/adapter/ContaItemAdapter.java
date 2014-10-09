package com.login.android.cardapio.adapter;

import java.text.NumberFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.login.android.cardapio.R;
import com.login.android.cardapio.model.Conta;
import com.login.android.cardapio.model.PedidoItem;

public class ContaItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Conta conta;

	public ContaItemAdapter(Context _context, Conta conta) {
		this.mInflater = LayoutInflater.from(_context);
		this.setConta(conta);
	}

	public int getCount() {
		if (this.conta != null && this.conta.getListPedido().size() != 0 && this.conta.getListPedido().get(0).getListPedidoItem().size() != 0) {
			return this.getConta().getListPedido().get(0).getListPedidoItem().size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		if (this.conta != null && this.conta.getListPedido().size() != 0 && this.conta.getListPedido().get(0).getListPedidoItem().size() != 0) {
			return this.getConta().getListPedido().get(0).getListPedidoItem().get(arg0);
		} else {
			return 0;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("UseValueOf")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (this.conta != null && this.conta.getListPedido().size() != 0 && this.conta.getListPedido().get(0).getListPedidoItem().size() != 0) {
			convertView = mInflater.inflate(R.layout.tab_fragment_conta_item_adapter, null);
			PedidoItem pedidoItem = this.conta.getListPedido().get(0).getListPedidoItem().get(position);

			NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

			if ((position % 2) == 0) {
				((LinearLayout) convertView.findViewById(R.id.tab_fragment_conta_item_adapeter_linear_layout)).setBackgroundResource(R.color.branco);
			} else {
				((LinearLayout) convertView.findViewById(R.id.tab_fragment_conta_item_adapeter_linear_layout)).setBackgroundResource(R.color.bg_system);
			}

			((TextView) convertView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_nome)).setText(pedidoItem.getSubItem().getDescricao() + " - " + pedidoItem.getSubItem().getDescricaoTipoQuantidade());
			((TextView) convertView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_valor_unitario)).setText(format.format(new Double(pedidoItem.getSubItem().getValor())));
			((TextView) convertView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_qtd)).setText(pedidoItem.getQuantidade().toString());
			((TextView) convertView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_valor_total)).setText(format.format(new Double(pedidoItem.getValor_total())));
		}

		return convertView;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
