package com.login.beachstop.android.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.login.beachstop.android.business.Observable;
import com.login.beachstop.android.model.ServerResponse;

public class JSONTokenUtil extends AsyncTask<String, Void, ServerResponse> {

	private Observable observable;

	public JSONTokenUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(String... params) {
		String regId = params == null || params.length == 0 ? null : params[0];
		return new HttpUtil().getJSONFromURLPost(getUrl(), parseToNameValuePair(regId));
	}

	private List<NameValuePair> parseToNameValuePair(String regId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("token", regId));
		return nameValuePairs;
	}

	private String getUrl() {
		return Constantes.URL_WS + "/tokens";
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
