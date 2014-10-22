package com.login.beachstop.android.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.business.Observable;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.ItemCardapioSubItem;
import com.login.beachstop.android.model.ServerResponse;

public class JSONItemCardapioUtil extends AsyncTask<List<Long>, Void, ServerResponse> {

	private Observable observable;

	public JSONItemCardapioUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(List<Long>... params) {

		List<Long> categoriaCardapio = params == null || params.length == 0 ? null : params[0];

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(getUrl(categoriaCardapio));

		if (serverResponse.isOK()) {
			try {
				serverResponse.setReturnObject(parseToObj((JSONObject) serverResponse.getReturnObject()));
				return serverResponse;
			} catch (JSONException ex) {
				return null;
			}
		}
		return null;
	}

	private String getUrl(List<Long> categoriaCardapio) {
		return Constantes.URL_WS + "/itens/keymobile/" + Constantes.KEYMOBILE + "/categorias/" + joinToString(categoriaCardapio, ",");
	}

	public static String joinToString(Collection<?> collection, CharSequence separator) {

		if (collection.isEmpty()) {
			return "";
		} else {
			StringBuilder sepValueBuilder = new StringBuilder();
			for (Object obj : collection) {
				// Append the valuen and the separator even if it's the las
				// element
				sepValueBuilder.append(obj).append(separator);
			}
			// Remove the last separator
			sepValueBuilder.setLength(sepValueBuilder.length() - separator.length());
			return sepValueBuilder.toString();
		}
	}

	private List<ItemCardapio> parseToObj(JSONObject jsonObject) throws JSONException {

		List<ItemCardapio> listItemCardapio = new ArrayList<ItemCardapio>();
		JSONArray jsonArr = jsonObject.getJSONArray("");
		JSONObject jsonItemCardapio;

		JSONArray jsonArrSubItem;
		JSONObject jsonSubItemCardapio;

		ItemCardapio itemCardapio;
		ItemCardapioSubItem subItemCardapio;
		for (int i = 0; i < jsonArr.length(); i++) {
			itemCardapio = new ItemCardapio();
			jsonItemCardapio = jsonArr.getJSONObject(i).getJSONObject("item");

			itemCardapio.setId(jsonItemCardapio.getLong("id"));
			if (jsonItemCardapio.has("imagem")) {
				itemCardapio.setImagem(jsonItemCardapio.getString("imagem"));
			} else {
				itemCardapio.setImagem("");
			}

			if (jsonItemCardapio.has("nome")) {
				itemCardapio.setNome(jsonItemCardapio.getString("nome"));
			} else {
				itemCardapio.setNome("");
			}

			if (jsonItemCardapio.has("descricao")) {
				itemCardapio.setDescricao(jsonItemCardapio.getString("descricao"));
			} else {
				itemCardapio.setDescricao("");
			}

			if (jsonItemCardapio.has("ingredientes")) {
				itemCardapio.setIngredientes(jsonItemCardapio.getString("ingredientes"));
			} else {
				itemCardapio.setIngredientes("");
			}

			if (jsonItemCardapio.has("guarnicoes")) {
				itemCardapio.setGuarnicoes(jsonItemCardapio.getString("guarnicoes"));
			} else {
				itemCardapio.setGuarnicoes("");
			}

			if (jsonItemCardapio.has("guarnicoes")) {
				itemCardapio.setTempoMedioPreparo(jsonItemCardapio.getLong("tempoMedioPreparo"));
			} else {
				itemCardapio.setTempoMedioPreparo(0l);
			}

			itemCardapio.setIdCategoriaCardapio(jsonItemCardapio.getJSONObject("empresaCategoriaCardapio").getJSONObject("categoriaCardapio").getLong("id"));

			itemCardapio.setSubItens(new ArrayList<ItemCardapioSubItem>());
			jsonArrSubItem = Utilitarios.getAlwaysJsonArray(jsonItemCardapio, "subItens");
			for (int j = 0; j < jsonArrSubItem.length(); j++) {
				subItemCardapio = new ItemCardapioSubItem();
				jsonSubItemCardapio = jsonArrSubItem.getJSONObject(j);

				subItemCardapio.setId(jsonSubItemCardapio.getLong("id"));
				subItemCardapio.setQuantidade(jsonSubItemCardapio.getLong("quantidade"));
				subItemCardapio.setDescricaoTipoQuantidade(jsonSubItemCardapio.getJSONObject("tipoQuantidade").getString("descricao"));
				subItemCardapio.setIdTipoQuantidade(jsonSubItemCardapio.getJSONObject("tipoQuantidade").getLong("id"));
				subItemCardapio.setValor(BigDecimal.valueOf(jsonSubItemCardapio.getDouble("valor")));
				subItemCardapio.setDescricao(jsonSubItemCardapio.getString("descricao"));
				subItemCardapio.setIdItemCardapio(itemCardapio.getId());

				itemCardapio.getSubItens().add(subItemCardapio);
			}

			listItemCardapio.add(itemCardapio);
		}

		return listItemCardapio;
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
