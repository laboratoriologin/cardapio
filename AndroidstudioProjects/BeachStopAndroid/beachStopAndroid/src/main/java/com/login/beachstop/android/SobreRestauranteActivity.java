package com.login.beachstop.android;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.login.beachstop.android.models.Empresa;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.EmpresaRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.actionbar.ActionBar;

import static com.login.beachstop.android.R.layout.activity_sobre_restaurante;

public class SobreRestauranteActivity extends DefaultActivity {

    protected WebView webView = null;
    protected ProgressDialog progressDialog;

    private ResponseListener listenerGetEmpresa = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    String data = ((Empresa) serverResponse.getReturnObject()).getHtml();
                    String html = "<html><body>";
                    String htmlFim = "</body></html>";

                    webView.loadDataWithBaseURL("", html + data + htmlFim, "text/html", "UTF-8", "");

                    progressDialog.dismiss();

                } else {

                    Toast.makeText(SobreRestauranteActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();

                }
            } else {

                Toast.makeText(SobreRestauranteActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();

            }

        }

    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_sobre_restaurante);

        ((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);

        this.webView = (WebView) findViewById(R.id.activity_sobre_restaurante_web_view);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);

        new EmpresaRequest(listenerGetEmpresa).getHtml();

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Buscando as informações do restaurante...");
        this.progressDialog.show();
        this.progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

}
