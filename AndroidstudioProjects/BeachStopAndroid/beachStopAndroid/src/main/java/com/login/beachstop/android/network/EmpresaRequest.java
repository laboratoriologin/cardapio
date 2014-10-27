package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Empresa;
import com.login.beachstop.android.models.ServerRequest;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

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

        String urlgetAtivo = Constantes.URL_WS + "/" + new Empresa().getServiceName() + "/1/keyCardapio";

        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);

        this.execute(serverRequest);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            Empresa empresa;

            try {

                Long keyCardapio = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("empresa").getLong(Constantes.KEY_CARDAPIO);

                empresa = new Empresa();
                empresa.setKeyCardapio(keyCardapio.toString());

                serverResponse.setReturnObject(empresa);


            } catch (JSONException e) {
                e.printStackTrace();

                serverResponse.setStatusCode(-1);
            }

        }

    }


}
