package com.login.android.cardapio.garcom.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.cardapio.garcom.business.Observable;
import com.login.android.cardapio.garcom.model.Conta;
import com.login.android.cardapio.garcom.model.ItemCardapioSubItem;
import com.login.android.cardapio.garcom.model.Pedido;
import com.login.android.cardapio.garcom.model.PedidoItem;
import com.login.android.cardapio.garcom.model.ServerResponse;

public class JSONHistoricoContaUtil extends AsyncTask<Long, Void, ServerResponse> {

	private Observable observable;

	public JSONHistoricoContaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Long... params) {

		Long nMesa = params == null || params.length == 0 ? null : params[0];

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(getUrl(nMesa));

		if (serverResponse.isOK()) {
			try {
				serverResponse.setReturnObject(parseToObj((JSONObject) serverResponse.getReturnObject()));
				return serverResponse;
			} catch (JSONException ex) {
				return serverResponse;
			}
		}
		return serverResponse;
	}

	private String getUrl(Long nMesa) {
		return Constantes.URL_WS + "/contas/historico/mesa/" + nMesa.toString() + "/empresa/" + Constantes.KEYMOBILE + "/log/false";
	}

	private Conta parseToObj(JSONObject jsonObject) throws JSONException {

		Conta conta = new Conta();

		if (jsonObject.has("conta")) {

			JSONObject jsonConta = jsonObject.getJSONObject("conta");

			conta.setId(jsonConta.getLong("id"));
			conta.setHorarioChegada(jsonConta.getString("horarioChegada"));
			conta.setMesa(jsonConta.getLong("mesa"));
			conta.setValor(jsonConta.getString("valor"));
			conta.setValorPago(jsonConta.getString("valorPago"));

			conta.setListPedido(new ArrayList<Pedido>());

			if (jsonConta.has("pedidoSubItem")) {

				JSONArray jsonArr = Utilitarios.getAlwaysJsonArray(jsonConta, "pedidoSubItem");

				Pedido pedido = new Pedido();
				pedido.setListPedidoItem(new ArrayList<PedidoItem>());
				PedidoItem pedidoItem;
				ItemCardapioSubItem subItemCardapio;
				for (int i = 0; i < jsonArr.length(); i++) {
					pedidoItem = new PedidoItem();
					pedidoItem.setQuantidade(jsonArr.getJSONObject(i).getLong("quantidade"));
					pedidoItem.setValor_total(jsonArr.getJSONObject(i).getString("valor"));

					subItemCardapio = new ItemCardapioSubItem();
					subItemCardapio.setDescricao(jsonArr.getJSONObject(i).getJSONObject("subitem").getJSONObject("item").getString("nome"));
					subItemCardapio.setValor(jsonArr.getJSONObject(i).getJSONObject("subitem").getString("valor"));
					subItemCardapio.setIdTipoQuantidade(0l);
					subItemCardapio.setDescricaoTipoQuantidade(jsonArr.getJSONObject(i).getJSONObject("subitem").getString("descricao"));
					pedidoItem.setSubItem(subItemCardapio);

					pedido.getListPedidoItem().add(pedidoItem);
				}
				conta.getListPedido().add(pedido);
			}

		}

		return conta;
	}

	@Override
	protected void onPostExecute(ServerResponse result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
