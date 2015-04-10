package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Empresa;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Argus on 24/10/2014.
 */
public class EmpresaRequest extends ObjectRequest<Empresa> {

    public EmpresaRequest(ResponseListener listener) {
        super(listener);
    }

    public void getKeyCardapio() {

        String urlgetAtivo = Constantes.URL_WS + "/" + new Empresa().getServiceName() + "/1";

        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);

        this.execute(serverRequest);
    }

    public void getHtml() {

        String urlgetAtivo = Constantes.URL_WS + "/" + new Empresa().getServiceName() + "/1/htmlEmpresa";

        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);

        this.execute(serverRequest);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            Empresa empresa;
            JSONObject jsonEmpresa;

            try {

                if (((JSONObject) serverResponse.getReturnObject()).has("empresa")) {

                    jsonEmpresa = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("empresa");

                    empresa = new Empresa();
                    empresa.setId(jsonEmpresa.has("id") ? jsonEmpresa.getLong("id") : null);
                    empresa.setEmail(jsonEmpresa.has("email") ? jsonEmpresa.getString("email") : "");
                    empresa.setEndereco(jsonEmpresa.has("endereco") ? jsonEmpresa.getString("endereco") : "");
                    empresa.setHtml(jsonEmpresa.has("htmlEmpresa") ? jsonEmpresa.getString("htmlEmpresa") : "");
                    empresa.setKeyCardapio((jsonEmpresa.has("keyCardapio") ? jsonEmpresa.getLong("keyCardapio") : "").toString());
                    empresa.setKeyMobile(jsonEmpresa.has("keyMobile") ? jsonEmpresa.getString("keyMobile") : "");
                    empresa.setLatitude((jsonEmpresa.has("latitude") ? jsonEmpresa.getLong("latitude") : "").toString());
                    empresa.setLongitude((jsonEmpresa.has("longitude") ? jsonEmpresa.getLong("longitude") : "").toString());
                    empresa.setNome(jsonEmpresa.has("nome") ? jsonEmpresa.getString("nome") : "");
                    empresa.setTelefone((jsonEmpresa.has("telefone") ? jsonEmpresa.getLong("telefone") : "").toString());
                    empresa.setCnpj(jsonEmpresa.has("cnpj") ? jsonEmpresa.getString("cnpj") : "");

                    serverResponse.setReturnObject(empresa);

                } else {

                    serverResponse.setReturnObject(new Empresa());

                }


            } catch (JSONException e) {

                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }

        }

    }

}