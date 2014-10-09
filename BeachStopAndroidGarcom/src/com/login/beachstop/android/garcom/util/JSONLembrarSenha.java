package com.login.beachstop.android.garcom.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.garcom.business.Observable;
import com.login.beachstop.android.garcom.model.ServerResponse;
import com.login.beachstop.android.garcom.model.Usuario;



public class JSONLembrarSenha extends AsyncTask<Usuario, Void, ServerResponse> {

	private Observable observable;
	private Usuario itemUsuario;

	public JSONLembrarSenha(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Usuario... params) {
		
		Usuario usuario = params == null || params.length == 0 ? null : params[0];
		return new HttpUtil().getJSONFromURLPost(getUrl(), parseToNameValuePair(usuario));
		
	}
	
	private List<NameValuePair> parseToNameValuePair(Usuario usuario) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", usuario.getEmail().toString()));
		nameValuePairs.add(new BasicNameValuePair("empresa", Constantes.KEYMOBILE));
				return nameValuePairs;
	}

	private String getUrl() {
		return Constantes.URL_WS + "/usuarios/enviarE" +
				"mail";
	}

	private List<Usuario> parseToObj(JSONObject jsonObject) throws JSONException {

		List<Usuario> listUsuario = new ArrayList<Usuario>();
		JSONArray jsonArr = jsonObject.getJSONArray("");
		Usuario itemCategoriaCardapio;

		for (int i = 0; i < jsonArr.length(); i++) {
			itemUsuario = new Usuario();
			itemUsuario.setId(jsonArr.getJSONObject(i).getJSONObject("usuario").getLong("id"));
			listUsuario.add(itemUsuario);
		}
		return listUsuario;
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
