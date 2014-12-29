package com.login.beachstop.android;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TabHost;

import com.login.beachstop.android.model.CategoriaCardapioItem;
import com.login.beachstop.android.sqlite.dao.DataManager;

public class DefaultActivity extends FragmentActivity {

	public DataManager getDataManager() {
		return ((CardapioApp) getApplication()).getDataManager();
	}

	public List<CategoriaCardapioItem> getListaItemCardapio() {
		return ((CardapioApp) getApplication()).getListaItemCardapio();
	}

	public void setListaItemCardapio(List<CategoriaCardapioItem> _listaItemCardapio) {
		((CardapioApp) getApplication()).setListaItemCardapio(_listaItemCardapio);
	}

	public void backPressed(View view) {
		super.onBackPressed();
	}

	public TabHost getTabHost() {
		return ((CardapioApp) getApplication()).getTabHost();
	}

	public void setTabHost(TabHost mTabHost) {
		((CardapioApp) getApplication()).setTabHost(mTabHost);
	}

	public Long getQtdMesa() {
		return ((CardapioApp) getApplication()).getQtdMesa();
	}

	public void setQtdMesa(Long qtdMesa) {
		((CardapioApp) getApplication()).setQtdMesa(qtdMesa);
	}
}
