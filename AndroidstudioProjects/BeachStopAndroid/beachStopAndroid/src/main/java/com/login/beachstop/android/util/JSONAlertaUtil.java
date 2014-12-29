package com.login.beachstop.android.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.login.beachstop.android.business.Observable;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.ServerResponse;

public class JSONAlertaUtil extends AsyncTask<Conta, Void, ServerResponse> {

	private Observable observable;

	public JSONAlertaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Conta... params) {

		Conta conta = params == null || params.length == 0 ? null : params[0];

		ServerResponse serverResponse = new HttpUtil().getJSONFromURLPost(getUrl(), parseToNameValuePair(conta));

		return serverResponse;
	}

	private List<NameValuePair> parseToNameValuePair(Conta conta) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("flagresolvido", "0"));
		nameValuePairs.add(new BasicNameValuePair("conta", conta.getId().toString()));
		nameValuePairs.add(new BasicNameValuePair("tipoalerta", "1"));

		return nameValuePairs;
	}

	private String getUrl() {
		return Constantes.URL_WS + "/alertas";
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
