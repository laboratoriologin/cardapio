package com.login.beachstop.android.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.business.Observable;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.ServerResponse;

public class JSONCheckContaAbertaUtil extends AsyncTask<Conta, Void, ServerResponse> {

	private Observable observable;

	public JSONCheckContaAbertaUtil(Observable observable) {
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

	private String getUrl(Conta argConta) {
		return Constantes.URL_WS + "/contas/" + argConta.getId() + "/flagAberto/";
	}

	private Boolean parseToObj(JSONObject jsonObject) throws JSONException {
		return jsonObject.getJSONObject("conta").getBoolean("flagAberto");
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
