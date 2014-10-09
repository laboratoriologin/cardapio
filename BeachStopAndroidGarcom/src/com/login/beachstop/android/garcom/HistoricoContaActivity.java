package com.login.beachstop.android.garcom;

import java.text.NumberFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.garcom.business.BusinessResult;
import com.login.beachstop.android.garcom.business.ContaBS;
import com.login.beachstop.android.garcom.model.Conta;
import com.login.beachstop.android.garcom.model.PedidoItem;
import com.login.beachstop.android.garcom.model.ServerResponse;
import com.login.beachstop.android.garcom.util.Constantes;
import com.login.beachstop.android.garcom.view.ActionBar;

public class HistoricoContaActivity extends DefaultActivity implements BusinessResult {

	private Conta conta;
	private TableLayout tableLayoutItemConta;
	private TextView textViewSemPedido;
	private TextView textViewValorTotal;
	private Double valorTotal;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historico_conta);
		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.tableLayoutItemConta = (TableLayout) this.findViewById(R.id.activity_conta_list_view_item_conta);
		this.textViewSemPedido = (TextView) this.findViewById(R.id.activity_conta_text_view_sem_conta);
		this.textViewValorTotal = (TextView) this.findViewById(R.id.activity_conta_text_view_valor);

		((Button) this.findViewById(R.id.activity_conta_button_pesquisa_mesa)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nMesa = ((EditText) findViewById(R.id.activity_conta_edittext_pesquisa_mesa)).getEditableText().toString();

				if (!"".equals(nMesa)) {

					progressDialog = new ProgressDialog(HistoricoContaActivity.this);

					progressDialog.setMessage("Buscando histórico...");

					progressDialog.show();
					progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

					progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

					new ContaBS(HistoricoContaActivity.this).getHistoricoConta(Long.valueOf(nMesa));
				}
			}
		});

	}

	@SuppressLint("UseValueOf")
	public void bindTableItemConta() {

		PedidoItem pedidoItem;
		View itemContaView;
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		this.tableLayoutItemConta.removeAllViews();

		for (int position = 0; position < this.conta.getListPedido().get(0).getListPedidoItem().size(); position++) {
			pedidoItem = this.conta.getListPedido().get(0).getListPedidoItem().get(position);

			itemContaView = TableRow.inflate(this, R.layout.activity_historico_conta_item, null);

			if ((position % 2) == 0) {
				((TableRow) itemContaView.findViewById(R.id.activity_conta_item_adapeter_linear_layout)).setBackgroundResource(R.color.branco);
			} else {
				((TableRow) itemContaView.findViewById(R.id.activity_conta_item_adapeter_linear_layout)).setBackgroundResource(R.color.bg_system);
			}

			((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_nome)).setText(pedidoItem.getSubItem().getDescricao() + " - " + pedidoItem.getSubItem().getDescricaoTipoQuantidade());
			((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_valor_unitario)).setText(format.format(new Double(pedidoItem.getSubItem().getValor())));
			((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_qtd)).setText(pedidoItem.getQuantidade().toString());
			((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_valor_total)).setText(format.format((new Double(pedidoItem.getValor_total()) * new Double(pedidoItem.getQuantidade()))));

			this.tableLayoutItemConta.addView(itemContaView);

		}
	}

	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		progressDialog.dismiss();

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

				this.valorTotal = new Double(this.conta.getValor()) + new Double(this.conta.getValorPago());

				this.textViewValorTotal.setText(format.format(this.valorTotal));

			} catch (Exception e) {
				Toast.makeText(this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}
		} else {

			if (ServerResponse.SC_NO_CONTENT == serverResponse.getStatusCode()) {
				this.textViewSemPedido.setVisibility(TextView.VISIBLE);
				this.tableLayoutItemConta.setVisibility(TableLayout.GONE);
				this.textViewValorTotal.setText(format.format(0));
			} else {
				Toast.makeText(this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}