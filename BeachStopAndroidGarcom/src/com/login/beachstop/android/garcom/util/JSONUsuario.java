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
import com.login.beachstop.android.garcom.model.Empresa;
import com.login.beachstop.android.garcom.model.ServerResponse;
import com.login.beachstop.android.garcom.model.Usuario;

public class JSONUsuario extends AsyncTask<Usuario, Void, ServerResponse> {

	private Observable observable;

	public JSONUsuario(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Usuario... params) {
		Usuario usuario = params == null || params.length == 0 ? null : params[0];

		ServerResponse serverResponse = new HttpUtil().getJSONFromURLPost(getUrl(), parseToNameValuePair(usuario));

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

	private List<NameValuePair> parseToNameValuePair(Usuario usuario) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("login", usuario.getLogin()));

		nameValuePairs.add(new BasicNameValuePair("senha", usuario.getSenha().toString()));

		nameValuePairs.add(new BasicNameValuePair("empresa", Constantes.KEYMOBILE));

		return nameValuePairs;

	}

	private String getUrl() {

		return Constantes.URL_WS + "/usuarios/login";

	}

	private Usuario parseToObj(JSONObject jsonObject) throws JSONException {

		Usuario usuario = new Usuario();

		JSONObject jsonUsuario = jsonObject.getJSONObject("usuario");

		usuario.setId(jsonUsuario.getLong("id"));

		usuario.setNome(jsonUsuario.getString("nome"));

		usuario.setLogin(jsonUsuario.getString("login"));

		usuario.setEmail(jsonUsuario.getString("email"));

		Empresa empresa = new Empresa();

		empresa.setId(jsonUsuario.getJSONObject("empresa").getLong("id"));

		usuario.setEmpresa(empresa);

		JSONArray mesas = Utilitarios.getAlwaysJsonArray(jsonUsuario, "listMesa");
		
		usuario.setMesas(new ArrayList<Long>());

		for (int i = 0; i < mesas.length(); i++) {

			usuario.getMesas().add(mesas.getLong(i));

		}

		return usuario;
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
