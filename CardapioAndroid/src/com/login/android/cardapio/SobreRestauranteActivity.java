package com.login.android.cardapio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.login.android.cardapio.business.BusinessResult;
import com.login.android.cardapio.business.EmpresaBS;
import com.login.android.cardapio.model.Empresa;
import com.login.android.cardapio.model.ServerResponse;
import com.login.android.cardapio.view.ActionBar;

public class SobreRestauranteActivity extends DefaultActivity implements BusinessResult {

	private WebView webView = null;
	private String url = null;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre_restaurante);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);

		this.webView = (WebView) findViewById(R.id.activity_sobre_restaurante_web_view);
		this.webView.getSettings().setJavaScriptEnabled(true);
		this.webView.getSettings().setLoadWithOverviewMode(true);
		this.webView.getSettings().setUseWideViewPort(true);

		new EmpresaBS(this).getEmpresa();

	}

	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {

			String data = ((Empresa) serverResponse.getReturnObject()).getDadosEmpresa();
			String html = "<html><body>";
			String htmlFim = "</body></html>";

			this.webView.loadDataWithBaseURL("", html + data + htmlFim, "text/html", "UTF-8", "");
		} else {
			Toast.makeText(this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
		}

	}
}
