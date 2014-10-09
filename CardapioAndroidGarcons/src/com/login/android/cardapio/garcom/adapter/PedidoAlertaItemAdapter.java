package com.login.android.cardapio.garcom.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.cardapio.garcom.DefaultActivity;
import com.login.android.cardapio.garcom.PedidoActivity;
import com.login.android.cardapio.garcom.R;
import com.login.android.cardapio.garcom.model.Alerta;
import com.login.android.cardapio.garcom.model.Pedido;
import com.login.android.cardapio.garcom.model.PedidoAlertaItem;
import com.login.android.cardapio.garcom.model.PedidoItem;
import com.login.android.cardapio.garcom.model.ServerResponse;
import com.login.android.cardapio.garcom.util.Constantes;
import com.login.android.cardapio.garcom.util.HttpUtil;

public class PedidoAlertaItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	List<PedidoAlertaItem> pedidosAlertas;
	private DefaultActivity activity;

	public PedidoAlertaItemAdapter(DefaultActivity activity, List<PedidoAlertaItem> pedidosAlertas) {
		this.mInflater = LayoutInflater.from(activity);
		this.activity = activity;
		this.pedidosAlertas = pedidosAlertas;
	}

	@Override
	public int getCount() {
		return this.pedidosAlertas.size();
	}

	@Override
	public Object getItem(int position) {
		return this.pedidosAlertas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.pedidosAlertas.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		PedidoAlertaItem item = this.pedidosAlertas.get(position);

		if (item.getAlerta() != null) {
			return this.getAlertaView(position, item.getAlerta(), convertView);
		}

		if (item.getPedido() != null) {
			return this.getPedidoView(position, item.getPedido(), convertView);
		}

		return null;

	}

	public View getAlertaView(int position, Alerta alerta, View convertView) {

		convertView = mInflater.inflate(R.layout.list_view_alerta_item, null);

		TableLayout table = (TableLayout) convertView.findViewById(R.id.list_view_alerta_item_table_layout);

		table.removeAllViews();

		View itemView = mInflater.inflate(R.layout.list_view_alerta_item_row, null);

		((TableRow) itemView.findViewById(R.id.list_view_alerta_item_row_linear_layout)).setBackgroundColor(Color.WHITE);

		((TextView) itemView.findViewById(R.id.list_view_alerta_item_row_text_view_mesa)).setText("Mesa " + alerta.getMesa().toString());
		((TextView) itemView.findViewById(R.id.list_view_alerta_item_row_text_view_tempo)).setText(alerta.getTempo());
		((TextView) itemView.findViewById(R.id.list_view_alerta_item_row_text_view_tipo_alerta)).setText(alerta.getTipoAlerta().getDescricao());

		((Button) itemView.findViewById(R.id.list_view_alerta_item_row_button_aprovar)).setOnClickListener(resolverAlerta);

		((Button) itemView.findViewById(R.id.list_view_alerta_item_row_button_aprovar)).setTag(Integer.valueOf(position));

		table.addView(itemView);

		return convertView;

	}

	public View getPedidoView(int position, Pedido pedido, View convertView) {

		convertView = mInflater.inflate(R.layout.list_view_pedido_item, null);

		TableLayout table = (TableLayout) convertView.findViewById(R.id.list_view_pedido_item_table_layout);

		table.removeAllViews();

		View itemView = null;

		for (PedidoItem item : pedido.getListPedidoItem()) {

			itemView = mInflater.inflate(R.layout.list_view_pedido_item_row, null);

			((TextView) itemView.findViewById(R.id.list_view_pedido_item_row_text_view_quantidade)).setText(item.getSubItem().getQuantidade().toString());

			((TextView) itemView.findViewById(R.id.list_view_pedido_item_row_text_view_descricao)).setText(item.getItemCardapio().getNome() + " - " + item.getSubItem().getDescricao());

			table.addView(itemView);

		}

		((TextView) convertView.findViewById(R.id.list_view_pedido_item_text_view_mesa)).setText("Pedido: Mesa " + pedido.getMesa());

		((TextView) convertView.findViewById(R.id.list_view_pedido_item_text_view_observacao)).setText(pedido.getObservacao());

		((ImageButton) convertView.findViewById(R.id.list_view_pedido_item_button_aprovar)).setOnClickListener(aprovar);

		((ImageButton) convertView.findViewById(R.id.list_view_pedido_item_button_aprovar)).setTag(Integer.valueOf(position));

		((ImageButton) convertView.findViewById(R.id.list_view_pedido_item_button_editar)).setOnClickListener(editar);

		((ImageButton) convertView.findViewById(R.id.list_view_pedido_item_button_editar)).setTag(Integer.valueOf(position));

		((ImageButton) convertView.findViewById(R.id.list_view_pedido_item_button_cancelar)).setOnClickListener(cancelar);

		((ImageButton) convertView.findViewById(R.id.list_view_pedido_item_button_cancelar)).setTag(Integer.valueOf(position));

		return convertView;

	}

	// ////////////////////////////////////////////////////////////
	// RESOLVER ALERTA
	// ////////////////////////////////////////////////////////////
	private OnClickListener resolverAlerta = new OnClickListener() {

		@Override
		public void onClick(View view) {

			Integer posicao = (Integer) view.getTag();

			if (pedidosAlertas.get(posicao).isExecutando()) {

				Toast.makeText(mInflater.getContext(), "Já existe uma operação em execução deste pedido", Toast.LENGTH_SHORT).show();

			} else {

				Toast.makeText(mInflater.getContext(), "Aprovando chamado...", Toast.LENGTH_SHORT).show();

				pedidosAlertas.get(posicao).setExecutando(true);

				new ResolverAlertaTask(view).execute(pedidosAlertas.get(posicao.intValue()));

				Button button = (Button) view;

				button.setPressed(true);

			}

		}

	};

	private class ResolverAlertaTask extends AsyncTask<PedidoAlertaItem, Integer, ServerResponse> {

		private PedidoAlertaItem pedidoAlertaItem;
		private View view;

		public ResolverAlertaTask(View view) {

			this.view = view;

		}

		@Override
		protected ServerResponse doInBackground(PedidoAlertaItem... arg0) {

			pedidoAlertaItem = arg0[0];

			return new HttpUtil().getJSONFromURLPut(Constantes.URL_WS + "/alertas/" + pedidoAlertaItem.getAlerta().getId(), new ArrayList<NameValuePair>());

		}

		protected void onPostExecute(ServerResponse result) {

			if (result != null && result.isOK()) {

				if (pedidosAlertas.indexOf(this.pedidoAlertaItem) >= 0) {
					pedidosAlertas.remove(this.pedidoAlertaItem);
				}

				notifyDataSetChanged();

			} else {

				Toast.makeText(mInflater.getContext(), "Ocorreu um erro ao resolver chamado da mesa " + pedidoAlertaItem.getAlerta().getMesa(), Toast.LENGTH_SHORT).show();

				view.setPressed(false);

				pedidoAlertaItem.setExecutando(false);

			}

		}

	}

	// ////////////////////////////////////////////////////////////
	// Aprovar Pedido
	// ////////////////////////////////////////////////////////////

	private OnClickListener aprovar = new OnClickListener() {

		@Override
		public void onClick(View view) {

			Integer posicao = (Integer) view.getTag();

			PedidoAlertaItem pedidoAlertaItem = pedidosAlertas.get(posicao);

			if (pedidoAlertaItem.isExecutando()) {

				Toast.makeText(mInflater.getContext(), "Já existe uma operação em execução deste pedido", Toast.LENGTH_SHORT).show();

			} else {

				Toast.makeText(mInflater.getContext(), "Aprovando pedido...", Toast.LENGTH_SHORT).show();

				pedidoAlertaItem.setExecutando(true);

				ImageButton button = (ImageButton) view;

				button.setPressed(true);

				new AprovarPedidoTask(button).execute(pedidoAlertaItem);

			}

		}

	};

	private class AprovarPedidoTask extends AsyncTask<PedidoAlertaItem, Integer, ServerResponse> {

		private PedidoAlertaItem pedidoAlertaItem;
		private Integer posicao;
		private ImageButton button;

		public AprovarPedidoTask(ImageButton button) {

			this.button = button;

			this.posicao = (Integer) button.getTag();

			this.button.setPressed(true);

		}

		@Override
		protected ServerResponse doInBackground(PedidoAlertaItem... arg0) {

			pedidoAlertaItem = arg0[0];

			List<NameValuePair> parametros = new ArrayList<NameValuePair>();

			parametros.add(new BasicNameValuePair("usuario", activity.getUsuario().getId().toString()));

			return new HttpUtil().getJSONFromURLPut(Constantes.URL_WS + "/pedidos/aprovar/" + pedidoAlertaItem.getPedido().getId(), parametros);

		}

		protected void onPostExecute(ServerResponse result) {

			if (result != null && result.isOK()) {

				if (pedidosAlertas.indexOf(pedidoAlertaItem) >= 0) {

					pedidosAlertas.remove(pedidoAlertaItem);

				}

				notifyDataSetChanged();

			} else {

				Toast.makeText(mInflater.getContext(), "Ocorreu um erro ao aprovar pedido da linha " + posicao, Toast.LENGTH_SHORT).show();

				pedidoAlertaItem.setExecutando(false);

				button.setPressed(false);

			}

		}

	}

	// ////////////////////////////////////////////////////////////
	// CANCELAR
	// ////////////////////////////////////////////////////////////

	private OnClickListener cancelar = new OnClickListener() {

		@Override
		public void onClick(View view) {

			Integer posicao = (Integer) view.getTag();

			if (pedidosAlertas.get(posicao).isExecutando()) {

				Toast.makeText(mInflater.getContext(), "Já existe uma operação em execução deste pedido", Toast.LENGTH_SHORT).show();

			} else {

				Toast.makeText(mInflater.getContext(), "Cancelando pedido...", Toast.LENGTH_SHORT).show();

				pedidosAlertas.get(posicao).setExecutando(true);

				PedidoAlertaItem pedidoAlertaItem = pedidosAlertas.get(posicao);

				new CancelarPedidoTask((ImageButton) view).execute(pedidoAlertaItem);

			}

		}

	};

	private class CancelarPedidoTask extends AsyncTask<PedidoAlertaItem, Integer, ServerResponse> {

		private PedidoAlertaItem pedidoAlertaItem;
		private Integer posicao;
		private ImageButton button;

		public CancelarPedidoTask(ImageButton button) {

			this.button = button;

			this.posicao = (Integer) button.getTag();

			this.button.setPressed(true);

		}

		@Override
		protected ServerResponse doInBackground(PedidoAlertaItem... arg0) {

			pedidoAlertaItem = arg0[0];

			List<NameValuePair> parametros = new ArrayList<NameValuePair>();

			parametros.add(new BasicNameValuePair("usuario", activity.getUsuario().getId().toString()));

			return new HttpUtil().getJSONFromURLPut(Constantes.URL_WS + "/pedidos/cancelar/" + pedidoAlertaItem.getPedido().getId(), parametros);

		}

		protected void onPostExecute(ServerResponse result) {

			if (result != null && result.isOK()) {

				if (pedidosAlertas.indexOf(pedidoAlertaItem) >= 0) {
					pedidosAlertas.remove(pedidoAlertaItem);
				}

				notifyDataSetChanged();

			} else {

				Toast.makeText(mInflater.getContext(), "Ocorreu um erro ao cancelar pedido da linha " + posicao, Toast.LENGTH_SHORT).show();

				pedidoAlertaItem.setExecutando(false);

				button.setPressed(false);

			}

		}

	}

	private OnClickListener editar = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Integer posicao = (Integer) v.getTag();

			if (pedidosAlertas.get(posicao).isExecutando()) {

				Toast.makeText(mInflater.getContext(), "Já existe uma operação em execução deste pedido", Toast.LENGTH_SHORT).show();

			} else {

				PedidoAlertaItem pedidoAlertaItem = pedidosAlertas.get(posicao);

				Intent intent = new Intent(mInflater.getContext(), PedidoActivity.class);

				intent.putExtra(Constantes.PARAMETRO_PEDIDO_EDITAR, pedidoAlertaItem.getPedido());

				mInflater.getContext().startActivity(intent);

			}

		}

	};

}
