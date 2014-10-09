package com.login.android.cardapio.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.cardapio.business.Observable;
import com.login.android.cardapio.model.Conta;
import com.login.android.cardapio.model.Pedido;
import com.login.android.cardapio.model.PedidoItem;
import com.login.android.cardapio.model.ServerResponse;
import com.login.android.cardapio.model.ItemCardapioSubItem;

public class JSONContaAllUtil extends AsyncTask<Conta, Void, ServerResponse> {

	private Observable observable;

	public JSONContaAllUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Conta... params) {

		Conta conta = params == null || params.length == 0 ? null : params[0];

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(getUrl(conta));

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

	private String getUrl(Conta conta) {
		return Constantes.URL_WS + "/contas/" + conta.getId().toString();
	}

	private Conta parseToObj(JSONObject jsonObject) throws JSONException {

		Conta contaRetorno = new Conta();
		contaRetorno.setId(jsonObject.getJSONObject("conta").getLong("id"));
		contaRetorno.setMesa(jsonObject.getJSONObject("conta").getLong("mesa"));
		contaRetorno.setHorarioChegada(jsonObject.getJSONObject("conta").getString("horarioChegada"));
		contaRetorno.setValor(jsonObject.getJSONObject("conta").getString("valor"));
		contaRetorno.setValorPago(jsonObject.getJSONObject("conta").getString("valorPago"));
		contaRetorno.setListPedido(new ArrayList<Pedido>());

		if (jsonObject.getJSONObject("conta").has("pedidoSubItem")) {

			JSONArray jsonArr = Utilitarios.getAlwaysJsonArray(jsonObject.getJSONObject("conta"), "pedidoSubItem");

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
			contaRetorno.getListPedido().add(pedido);
		}
		return contaRetorno;
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
