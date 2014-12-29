package com.login.beachstop.android;

import java.util.List;

import android.app.Application;
import android.widget.TabHost;

import com.login.beachstop.android.model.CategoriaCardapioItem;
import com.login.beachstop.android.sqlite.dao.DataManager;

public class CardapioApp extends Application {

	private DataManager dataManager;
	private List<CategoriaCardapioItem> listaItemCardapio;
	private TabHost mTabHost;
	private Long qtdMesa;

	@Override
	public void onCreate() {
		super.onCreate();
		setDataManager(new DataManager(this));

	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public List<CategoriaCardapioItem> getListaItemCardapio() {
		return listaItemCardapio;
	}

	public void setListaItemCardapio(List<CategoriaCardapioItem> listaItemCardapio) {
		this.listaItemCardapio = listaItemCardapio;
	}

	public TabHost getTabHost() {
		return mTabHost;
	}

	public void setTabHost(TabHost mTabHost) {
		this.mTabHost = mTabHost;
	}

	/**
	 * @return the qtdMesa
	 */
	public Long getQtdMesa() {
		return qtdMesa;
	}

	/**
	 * @param qtdMesa
	 *            the qtdMesa to set
	 */
	public void setQtdMesa(Long qtdMesa) {
		this.qtdMesa = qtdMesa;
	}
}
