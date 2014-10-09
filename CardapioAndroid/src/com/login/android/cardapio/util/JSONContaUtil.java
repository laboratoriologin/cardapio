package com.login.android.cardapio.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.cardapio.business.Observable;
import com.login.android.cardapio.model.Conta;
import com.login.android.cardapio.model.ServerResponse;

public class JSONContaUtil extends AsyncTask<Conta, Void, ServerResponse> {

	private Observable observable;

	public JSONContaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Conta... params) {

		Conta conta = params == null || params.length == 0 ? null : params[0];

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mesa", conta.getMesa().toString()));
		nameValuePairs.add(new BasicNameValuePair("empresa.keymobile", Constantes.KEYMOBILE));

		ServerResponse serverResponse;
		if (conta.getId() != null && conta.getId() > 0) {
			nameValuePairs.add(new BasicNameValuePair("id", conta.getId().toString()));
			serverResponse = new HttpUtil().getJSONFromURLPut(getUrl() + "/" + conta.getId() + "/", nameValuePairs);
		} else {
			serverResponse = new HttpUtil().getJSONFromURLPost(getUrl(), nameValuePairs);
		}

		if (serverResponse.isOK()) {
			try {
				serverResponse.setReturnObject(parseToObj((JSONObject) serverResponse.getReturnObject(), conta.getMesa()));
				return serverResponse;
			} catch (JSONException ex) {
				return serverResponse;
			}
		}
		return serverResponse;
	}

	private String getUrl() {
		return Constantes.URL_WS + "/contas";
	}

	private Conta parseToObj(JSONObject jsonObject, Long mesa) throws JSONException {

		Conta contaRetorno = new Conta();
		contaRetorno.setId(jsonObject.getJSONObject("conta").getLong("id"));
		contaRetorno.setMesa(mesa);
		contaRetorno.setHorarioChegada("00:00:00");
		contaRetorno.setValor("0");
		contaRetorno.setValorPago("0");

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
