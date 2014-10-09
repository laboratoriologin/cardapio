package com.login.beachstop.android.fragment;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.R;
import com.login.beachstop.android.PedidoActivity;
import com.login.beachstop.android.adapter.PedidoItemAdapter;
import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.business.PedidoBS;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;

public class TabFragmentPedido extends Fragment implements BusinessResult, ITabFragmentPedido {

	private View view;
	private ListView listViewPedidoItem;
	private PedidoItemAdapter pedidoItemAdapter;
	private Pedido pedido;
	private PedidoActivity pedidoActivity;
	private TextView valorTotal;
	private Button buttonEnviarPedido;
	private TextView textViewSemPedido;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		this.pedidoActivity = (PedidoActivity) inflater.getContext();

		this.view = inflater.inflate(R.layout.tab_fragment_pedido, container, false);

		this.listViewPedidoItem = (ListView) this.view.findViewById(R.id.tab_fragment_pedido_list_view_item_pedido);

		this.pedido = this.pedidoActivity.getDataManager().getPedido(false);

		this.valorTotal = ((TextView) this.view.findViewById(R.id.tab_fragment_pedido_text_view_valor));

		this.textViewSemPedido = ((TextView) this.view.findViewById(R.id.tab_fragment_pedido_text_view_sem_pedido));

		if (this.pedido != null && this.pedido.getListPedidoItem().size() != 0) {

			this.pedidoItemAdapter = new PedidoItemAdapter(this.view.getContext(), this.pedido, this.pedidoActivity.getDataManager(), this.valorTotal, this);
			this.listViewPedidoItem.setAdapter(this.pedidoItemAdapter);

			this.atualizaValorTotalPedido();
		}

		changeViewPedido();

		this.buttonEnviarPedido = ((Button) this.view.findViewById(R.id.tab_fragment_pedido_button_enviar_pedido));
		this.buttonEnviarPedido.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pedido = pedidoActivity.getDataManager().getPedido(false);

				if (pedido != null && pedido.getListPedidoItem().size() != 0) {

					for (PedidoItem pedidoItem : pedido.getListPedidoItem()) {
						if (pedidoItem.getQuantidade() == 0) {
							if (pedidoActivity.getDataManager().getPedidoItemDAO().delete(pedidoItem.getId())) {
								pedido.getListPedidoItem().remove(pedido.getListPedidoItem().indexOf(pedidoItem));
							}
						}
					}

					openDialog();

				} else {
					Toast.makeText(pedidoActivity, "Hum! Não pediu nada! \n Olhe as promoções!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		return view;
	}

	public void openDialog() {
		final Dialog dialog = new Dialog(this.pedidoActivity, R.style.style_bg_dialog_enviar_pedido);

		dialog.setContentView(R.layout.dialog_obs_enviar_pedido);
		dialog.setTitle("Alguma observação?");

		Button dialogButton = (Button) dialog.findViewById(R.id.dialog_obs_enviar_pedido__button_enviar_pedido);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				buttonEnviarPedido.setText("Enviando...");
				buttonEnviarPedido.setEnabled(false);

				pedido.setObservacao(((EditText) dialog.findViewById(R.id.dialog_obs_enviar_pedido_text_view_observacao)).getText().toString());
				new PedidoBS(TabFragmentPedido.this).save(pedido);
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	@Override
	public void onResume() {
		super.onResume();
		this.pedido = this.pedidoActivity.getDataManager().getPedido(false);
		changeViewPedido();

		if (this.pedido != null && this.pedido.getListPedidoItem().size() != 0) {

			if (this.pedidoItemAdapter != null) {
				this.pedidoItemAdapter.setPedido(this.pedido);
				this.pedidoItemAdapter.notifyDataSetChanged();
				this.atualizaValorTotalPedido();
			} else {

				this.pedidoItemAdapter = new PedidoItemAdapter(this.view.getContext(), this.pedido, this.pedidoActivity.getDataManager(), this.valorTotal, this);
				this.listViewPedidoItem.setAdapter(this.pedidoItemAdapter);

				this.atualizaValorTotalPedido();
			}
		}
	}

	public void atualizaValorTotalPedido() {
		BigDecimal valorTotalPedido = new BigDecimal(0);
		if (this.pedido != null) {

			for (PedidoItem pedidoItem : this.pedido.getListPedidoItem()) {
				valorTotalPedido = valorTotalPedido.add(new BigDecimal(pedidoItem.getQuantidade()).multiply(pedidoItem.getSubItem().getValorBigDecimal()));
			}
		}
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		this.valorTotal.setText(format.format(valorTotalPedido.doubleValue()));
		changeViewPedido();
	}

	public void changeViewPedido() {

		if (this.pedido != null && this.pedido.getListPedidoItem().size() != 0) {
			this.textViewSemPedido.setVisibility(TextView.GONE);
			this.listViewPedidoItem.setVisibility(ListView.VISIBLE);
		} else {
			this.textViewSemPedido.setVisibility(TextView.VISIBLE);
			this.listViewPedidoItem.setVisibility(ListView.GONE);
		}
	}

	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {
			try {
				this.pedido.setFinalizadoSys(true);
				this.pedidoActivity.getDataManager().getPedidoDAO().update(pedido, pedido.getId());
				this.pedido = null;
				this.atualizaValorTotalPedido();
				Toast.makeText(pedidoActivity, "Estamos preparando o seu pedido, divirta-se!", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(pedidoActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(pedidoActivity, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
		}

		buttonEnviarPedido.setText("Enviar o pedido");
		buttonEnviarPedido.setEnabled(true);

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}
}
