package com.login.beachstop.android.garcom.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.garcom.business.Observable;
import com.login.beachstop.android.garcom.model.CategoriaCardapioItem;
import com.login.beachstop.android.garcom.model.ServerResponse;

public class JSONCardapioUtil extends AsyncTask<String, Void, ServerResponse> {

	private Observable observable;

	public JSONCardapioUtil(Observable observable) {
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
		return Constantes.URL_WS + "/categorias_cardapio/empresa/" + Constantes.KEYMOBILE + "/id/";
	}

	private List<CategoriaCardapioItem> parseToObj(JSONObject jsonObject) throws JSONException {

		List<CategoriaCardapioItem> listItemCardapio = new ArrayList<CategoriaCardapioItem>();
		JSONArray jsonArr = jsonObject.getJSONArray("");
		CategoriaCardapioItem itemCategoriaCardapio;

		for (int i = 0; i < jsonArr.length(); i++) {
			itemCategoriaCardapio = new CategoriaCardapioItem();
			itemCategoriaCardapio.setId(jsonArr.getJSONObject(i).getJSONObject("categoriacardapio").getLong("id"));
			listItemCardapio.add(itemCategoriaCardapio);
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
