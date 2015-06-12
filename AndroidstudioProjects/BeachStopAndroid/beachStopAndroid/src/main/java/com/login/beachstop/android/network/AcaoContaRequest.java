package com.login.beachstop.android.network;

import com.login.beachstop.android.models.AcaoConta;
import com.login.beachstop.android.models.ServerRequest;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class AcaoContaRequest extends ObjectRequest<AcaoConta> {

    public AcaoContaRequest(ResponseListener listener) {
        super(listener);
    }

    public void chamarGgarcom(AcaoConta acaoConta) {
        String url = Constantes.URL_WS + "/" + new AcaoConta().getServiceName() + "/chamargarcom";
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    public void fecharConta(AcaoConta acaoConta) {
        String url = Constantes.URL_WS + "/" + new AcaoConta().getServiceName() + "/fercharconta";
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(AcaoConta acaoContao) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("acao", acaoContao.getAcaoId().toString()));
        nameValuePairs.add(new BasicNameValuePair("conta.id", acaoContao.getConta().getSistemaId().toString()));

        return nameValuePairs;
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
        }
    }
}
