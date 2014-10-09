package com.login.android.cardapio;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.login.android.cardapio.business.BusinessResult;
import com.login.android.cardapio.business.CardapioBS;
import com.login.android.cardapio.business.EmpresaBS;
import com.login.android.cardapio.business.ContaBS;
import com.login.android.cardapio.model.Empresa;
import com.login.android.cardapio.model.Conta;
import com.login.android.cardapio.model.CategoriaCardapioItem;
import com.login.android.cardapio.model.ServerResponse;
import com.login.android.cardapio.util.Constantes;

public class SplashActivity extends DefaultActivity implements BusinessResult {

	private final int SPLASH_MILIS = 1000;
	private ProgressBar progressBar;
	private TextView textView;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_splash);

		this.progressBar = (ProgressBar) this.findViewById(R.id.activity_splash_progressBar);
		this.textView = (TextView) this.findViewById(R.id.activity_splash_textView);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				progressBar.setVisibility(ProgressBar.VISIBLE);
				textView.setVisibility(TextView.VISIBLE);
				textView.setText(Constantes.MSG_SAUDACAO_DOIS);
				new EmpresaBS(SplashActivity.this).getEmpresa();

				Conta conta = getDataManager().getConta();

				if (conta != null) {
					new ContaBS(SplashActivity.this).chkContaAberta(conta);
				}
			}
		}, SPLASH_MILIS);

	}

	public void goCardapio() {
		Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
		SplashActivity.this.startActivity(mainIntent);
		SplashActivity.this.finish();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {

		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {
			if (serverResponse.getReturnObject() instanceof Boolean) {
				if (!((Boolean) serverResponse.getReturnObject())) {
					this.getDataManager().getContaDAO().deleteAll();
				}
			} else if (serverResponse.getReturnObject() instanceof Empresa) {

				List<Empresa> listChaves = this.getDataManager().getChaveCardapioEmpresaDAO().getAll();

				setQtdMesa(((Empresa) serverResponse.getReturnObject()).getQtdMesa());

				if (listChaves.size() == 0) {
					getCategoriaCardapioEmpresaNET();
				} else {
					Empresa chaveAtual = listChaves.get(0);

					if (chaveAtual.getChave().equals(((Empresa) serverResponse.getReturnObject()).getChave())) {
						getCategoriaCardapioEmpresaLOCAL();
						goCardapio();
					} else {
						this.getDataManager().getItemCardapioDAO().deleteAll();
						this.getDataManager().getCategoriaCardapioItemDAO().deleteAll();
						this.getDataManager().getItemCardapioSubItemDAO().deleteAll();
						getCategoriaCardapioEmpresaNET();
					}
				}

				try {
					if (this.getDataManager().getChaveCardapioEmpresaDAO().deleteAll())
						this.getDataManager().getChaveCardapioEmpresaDAO().save((Empresa) serverResponse.getReturnObject());
				} catch (Exception e) {
					textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
				}
			} else {

				super.setListaItemCardapio((List<CategoriaCardapioItem>) serverResponse.getReturnObject());
				CategoriaCardapioItem itemCategoriaCardapio = new CategoriaCardapioItem();
				itemCategoriaCardapio.setId(0l);
				super.getListaItemCardapio().add(itemCategoriaCardapio);

				try {
					for (CategoriaCardapioItem item : super.getListaItemCardapio()) {
						this.getDataManager().getCategoriaCardapioItemDAO().save(item);
					}
				} catch (Exception e) {
					textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
				}

				goCardapio();
			}
		} else {
			textView.setText(serverResponse.getMsgErro());
		}
	}

	private void getCategoriaCardapioEmpresaNET() {
		textView.setText(Constantes.MSG_SAUDACAO_UM);
		new CardapioBS(SplashActivity.this).getCardapioEmpresa();
	}

	private void getCategoriaCardapioEmpresaLOCAL() {
		textView.setText(Constantes.MSG_SAUDACAO_UM);
		super.setListaItemCardapio(this.getDataManager().getCategoriaCardapioItemDAO().getAllbyClause(null, null, null, null, null));
	}
}
