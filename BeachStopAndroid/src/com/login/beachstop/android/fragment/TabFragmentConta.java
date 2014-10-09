package com.login.beachstop.android.fragment;

import java.text.NumberFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.R;
import com.login.beachstop.android.PedidoActivity;
import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.business.ContaBS;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;

public class TabFragmentConta extends Fragment implements BusinessResult, ITabFragmentPedido {

	private View view;
	private Conta conta;
	private TableLayout tableLayoutItemConta;
	private TextView textViewSemPedido;
	private PedidoActivity pedidoActivity;
	private TextView textViewValorTotal;
	private CheckBox checkBoxDez;
	private Double valorTotal;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		this.pedidoActivity = (PedidoActivity) inflater.getContext();

		this.view = inflater.inflate(R.layout.tab_fragment_conta, container, false);
		this.tableLayoutItemConta = (TableLayout) this.view.findViewById(R.id.tab_fragment_conta_list_view_item_conta);
		this.textViewSemPedido = (TextView) this.view.findViewById(R.id.tab_fragment_conta_text_view_sem_conta);
		this.textViewValorTotal = (TextView) this.view.findViewById(R.id.tab_fragment_conta_text_view_valor);
		this.checkBoxDez = (CheckBox) this.view.findViewById(R.id.tab_fragment_conta_checkbox_gorjeta);

		if (this.pedidoActivity.getDataManager().getConta() != null) {
			new ContaBS(this).getContaFinal(this.pedidoActivity.getDataManager().getConta());
		}

		this.checkBoxDez.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox checkBox = (CheckBox) v;
				NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

				if (checkBox.isChecked()) {
					textViewValorTotal.setText(format.format(valorTotal + (valorTotal * 0.1)));
				} else {
					textViewValorTotal.setText(format.format(valorTotal));
				}

			}
		});

		return view;
	}

	@SuppressLint("UseValueOf")
	public void bindTableItemConta() {

		PedidoItem pedidoItem;
		View itemContaView;
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		this.tableLayoutItemConta.removeAllViews();

		for (int position = 0; position < this.conta.getListPedido().get(0).getListPedidoItem().size(); position++) {
			pedidoItem = this.conta.getListPedido().get(0).getListPedidoItem().get(position);

			itemContaView = TableRow.inflate(this.getView().getContext(), R.layout.tab_fragment_conta_item_adapter, null);

			if ((position % 2) == 0) {
				((TableRow) itemContaView.findViewById(R.id.tab_fragment_conta_item_adapeter_linear_layout)).setBackgroundResource(R.color.branco);
			} else {
				((TableRow) itemContaView.findViewById(R.id.tab_fragment_conta_item_adapeter_linear_layout)).setBackgroundResource(R.color.bg_system);
			}

			((TextView) itemContaView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_nome)).setText(pedidoItem.getSubItem().getDescricao() + " - " + pedidoItem.getSubItem().getDescricaoTipoQuantidade());
			((TextView) itemContaView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_valor_unitario)).setText(format.format(new Double(pedidoItem.getSubItem().getValor())));
			((TextView) itemContaView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_qtd)).setText(pedidoItem.getQuantidade().toString());
			((TextView) itemContaView.findViewById(R.id.tab_fragment_conta_item_adapeter_text_view_valor_total)).setText(format.format((new Double(pedidoItem.getValor_total()))));

			this.tableLayoutItemConta.addView(itemContaView);

		}
	}

	@SuppressLint("UseValueOf")
	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {
			try {
				this.conta = (Conta) serverResponse.getReturnObject();

				if (this.conta != null && this.conta.getListPedido().size() != 0 && this.conta.getListPedido().get(0).getListPedidoItem().size() != 0) {
					this.textViewSemPedido.setVisibility(TextView.GONE);
					this.tableLayoutItemConta.setVisibility(TableLayout.VISIBLE);

					bindTableItemConta();
				} else {
					this.textViewSemPedido.setVisibility(TextView.VISIBLE);
					this.tableLayoutItemConta.setVisibility(TableLayout.GONE);
				}

				NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
				this.valorTotal = new Double(this.conta.getValor()) + new Double(this.conta.getValorPago());

				this.textViewValorTotal.setText(format.format(this.valorTotal));

				this.checkBoxDez.setText(" + 10% (" + format.format(this.valorTotal * 0.1) + ")");

			} catch (Exception e) {
				Toast.makeText(pedidoActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(pedidoActivity, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onRefresh() {
		if (this.pedidoActivity.getDataManager().getConta() != null) {
			new ContaBS(this).getContaFinal(this.pedidoActivity.getDataManager().getConta());
		}
	}

}
