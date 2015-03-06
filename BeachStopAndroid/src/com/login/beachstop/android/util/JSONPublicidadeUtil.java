package com.login.beachstop.android.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.business.Observable;
import com.login.beachstop.android.model.Publicidade;
import com.login.beachstop.android.model.ServerResponse;

public class JSONPublicidadeUtil extends AsyncTask<String, Void, ServerResponse> {

	private Observable observable;

	public JSONPublicidadeUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(String... params) {

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(getUrl());

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

	private String getUrl() {
		return Constantes.URL_WS + "/publicidades/keymobile/" + Constantes.KEYMOBILE;
	}

	private List<Publicidade> parseToObj(JSONObject jsonObject) throws JSONException {

		List<Publicidade> listPublicidade = new ArrayList<Publicidade>();
		JSONArray jsonArr = jsonObject.getJSONArray("");
		Publicidade publicidade;
		JSONObject item;

		for (int i = 0; i < jsonArr.length(); i++) {
			item = jsonArr.getJSONObject(i).getJSONObject("publicidade");

			publicidade = new Publicidade();
			publicidade.setId(item.getLong("id"));
			publicidade.setDescricao(item.getString("descricao"));
			publicidade.setImagem(item.getString("imagem"));
			publicidade.setLink(item.getString("link"));
			publicidade.setNome(item.getString("nome"));
			publicidade.setTexto(item.getString("texto"));
			publicidade.setValor(item.getDouble("preco"));

			listPublicidade.add(publicidade);
		}
		return listPublicidade;
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