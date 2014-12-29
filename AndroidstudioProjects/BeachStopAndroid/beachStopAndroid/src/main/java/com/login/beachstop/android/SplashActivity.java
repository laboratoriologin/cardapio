package com.login.beachstop.android;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.business.CardapioBS;
import com.login.beachstop.android.business.EmpresaBS;
import com.login.beachstop.android.model.CategoriaCardapioItem;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.Empresa;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.util.Utilitarios;

public class SplashActivity extends DefaultActivity implements BusinessResult {

	private final int SPLASH_MILIS = 1000;
	private ProgressBar progressBar;
	private TextView textView;
	private ImageButton imageButton;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_splash);

		this.progressBar = (ProgressBar) this.findViewById(R.id.activity_splash_progressBar);
		this.textView = (TextView) this.findViewById(R.id.activity_splash_textView);
		this.imageButton = (ImageButton) this.findViewById(R.id.activity_splash_image_button);

		this.imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startDados();
			}
		});

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startDados();
			}
		}, SPLASH_MILIS);

	}

	public void startDados() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
		textView.setVisibility(TextView.VISIBLE);
		textView.setText(Constantes.MSG_SAUDACAO_DOIS);
		imageButton.setVisibility(ImageButton.GONE);
		new EmpresaBS(SplashActivity.this).getEmpresa();

		Conta conta = getDataManager().getConta();

		if (conta != null) {
			// new ContaBS(SplashActivity.this).chkContaAberta(conta);
			String horaAtual = Utilitarios.getHourNow();

			if ((Long.valueOf(horaAtual) - Long.valueOf(conta.getHorarioChegada())) >= (24 * 60 * 60)) {
				getDataManager().getContaDAO().deleteAll();
			}
		}
	}

	public void goCardapio() {
		Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
		SplashActivity.this.startActivity(mainIntent);
		SplashActivity.this.finish();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {

		if (result != null) {

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
						progressBar.setVisibility(ProgressBar.GONE);
						imageButton.setVisibility(ImageButton.VISIBLE);
						textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
					}
				} else {

					super.setListaItemCardapio((List<CategoriaCardapioItem>) serverResponse.getReturnObject());
//					CategoriaCardapioItem itemCategoriaCardapio = new CategoriaCardapioItem();
//					itemCategoriaCardapio.setId(0l);
//					super.getListaItemCardapio().add(itemCategoriaCardapio);

					try {
						for (CategoriaCardapioItem item : super.getListaItemCardapio()) {
							this.getDataManager().getCategoriaCardapioItemDAO().save(item);
						}
					} catch (Exception e) {
						progressBar.setVisibility(ProgressBar.GONE);
						imageButton.setVisibility(ImageButton.VISIBLE);
						textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
					}

					goCardapio();
				}
			} else {
				progressBar.setVisibility(ProgressBar.GONE);
				imageButton.setVisibility(ImageButton.VISIBLE);
				textView.setText(serverResponse.getMsgErro());
			}
		} else {
			progressBar.setVisibility(ProgressBar.GONE);
			imageButton.setVisibility(ImageButton.VISIBLE);
			textView.setText(Constantes.MSG_ERRO_NET);
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
