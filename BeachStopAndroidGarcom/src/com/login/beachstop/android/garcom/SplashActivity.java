package com.login.beachstop.android.garcom;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.login.beachstop.android.garcom.business.BusinessResult;
import com.login.beachstop.android.garcom.business.CardapioBS;
import com.login.beachstop.android.garcom.business.ChaveCardapioEmpresaBS;
import com.login.beachstop.android.garcom.business.ItemCardapioBS;
import com.login.beachstop.android.garcom.exception.PersistException;
import com.login.beachstop.android.garcom.model.CatagoriaCardapio;
import com.login.beachstop.android.garcom.model.CategoriaCardapioItem;
import com.login.beachstop.android.garcom.model.CategoriaCardapioItemSys;
import com.login.beachstop.android.garcom.model.ChaveCardapioEmpresa;
import com.login.beachstop.android.garcom.model.ItemCardapio;
import com.login.beachstop.android.garcom.model.ServerResponse;
import com.login.beachstop.android.garcom.util.Constantes;

public class SplashActivity extends DefaultActivity implements BusinessResult {

	private final int SPLASH_MILIS = 1000;
	private ProgressBar progressBar;
	private TextView textView;
	private ArrayList<CategoriaCardapioItemSys> listItemMenu;
	private CatagoriaCardapio menuCategoriaCardapio = new CatagoriaCardapio();

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
				textView.setText("Atualizando informações do sistema");
				new ChaveCardapioEmpresaBS(SplashActivity.this).getChaveCardapioEmpresa();
			}
		}, SPLASH_MILIS);

	}

	public void goCardapio() {

		if (getUsuario() == null) {

			Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);

			this.startActivity(mainIntent);

			this.finish();

		} else {

			Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);

			this.startActivity(mainIntent);

			this.finish();

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getBusinessResult(Object result) {

		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {

			if (serverResponse.getReturnObject() instanceof List) {

				if (((List) serverResponse.getReturnObject()).size() != 0) {

					if (((List) serverResponse.getReturnObject()).get(0) instanceof ItemCardapio) {
						try {
							for (ItemCardapio itemCardapio : (List<ItemCardapio>) serverResponse.getReturnObject()) {
								this.getDataManager().save(itemCardapio);
							}
							goCardapio();

						} catch (PersistException e) {
							textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
						}
					} else {
						super.setListaItemCardapio((List<CategoriaCardapioItem>) serverResponse.getReturnObject());

						try {
							for (CategoriaCardapioItem item : super.getListaItemCardapio()) {
								this.getDataManager().getCategoriaCardapioItemDAO().save(item);
							}
						} catch (Exception e) {
							textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
						}

						getItemMenuCardapio();
					}

				} else {
					textView.setText(Constantes.MSG_ERRO_NET);
				}

			} else if (serverResponse.getReturnObject() instanceof ChaveCardapioEmpresa) {

				List<ChaveCardapioEmpresa> listChaves = this.getDataManager().getChaveCardapioEmpresaDAO().getAll();
				this.setQtdMesa(((ChaveCardapioEmpresa) serverResponse.getReturnObject()).getQteMesa());

				if (listChaves.size() == 0) {
					getCategoriaCardapioEmpresaNET();
				} else {
					ChaveCardapioEmpresa chaveAtual = listChaves.get(0);

					if (chaveAtual.getChave().equals(((ChaveCardapioEmpresa) serverResponse.getReturnObject()).getChave())) {
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
						this.getDataManager().getChaveCardapioEmpresaDAO().save((ChaveCardapioEmpresa) serverResponse.getReturnObject());
				} catch (Exception e) {
					textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
				}
			}
		} else {
			textView.setText(serverResponse.getMsgErro());
		}
	}

	public void getItemMenuCardapio() {

		this.listItemMenu = new ArrayList<CategoriaCardapioItemSys>();

		List<ItemCardapio> listItemCardapio;
		List<Long> listItemBuscarSys = new ArrayList<Long>();

		for (CategoriaCardapioItem item : this.getListaItemCardapio()) {
			this.listItemMenu.add(this.menuCategoriaCardapio.getItemMenu(item.getId()));
			listItemCardapio = this.getDataManager().getAll(item.getId());
			if (listItemCardapio.size() == 0)
				listItemBuscarSys.add(item.getId());
		}

		if (listItemBuscarSys.size() != 0)
			new ItemCardapioBS(this).getCardapioEmpresa(listItemBuscarSys);
		else
			goCardapio();

	}

	private void getCategoriaCardapioEmpresaNET() {
		new CardapioBS(SplashActivity.this).getCardapioEmpresa();
	}

	private void getCategoriaCardapioEmpresaLOCAL() {
		super.setListaItemCardapio(this.getDataManager().getCategoriaCardapioItemDAO().getAllbyClause(null, null, null, null, null));
	}
}
