package com.login.beachstop.android.garcom;

import java.util.List;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.login.beachstop.android.garcom.model.CategoriaCardapioItem;
import com.login.beachstop.android.garcom.model.Usuario;
import com.login.beachstop.android.garcom.sqlite.dao.DataManager;

public class DefaultActivity extends FragmentActivity {

	public DataManager getDataManager() {
		return ((CardapioAppGarcom) getApplication()).getDataManager();
	}

	public void backPressed(View view) {
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (!(this instanceof LoginActivity) && !(this instanceof SplashActivity)) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu, menu);
			return true;
		} else
			return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_sair:
			setUsuario(null);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		case R.id.menu_historico:
			Intent i = new Intent(this, HistoricoContaActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public List<CategoriaCardapioItem> getListaItemCardapio() {
		return ((CardapioAppGarcom) getApplication()).getListaItemCardapio();
	}

	public void setListaItemCardapio(List<CategoriaCardapioItem> _listaItemCardapio) {
		((CardapioAppGarcom) getApplication()).setListaItemCardapio(_listaItemCardapio);
	}

	public Long getQtdMesa() {
		return ((CardapioAppGarcom) getApplication()).getQtdMesa();
	}

	public void setQtdMesa(Long qtdMesa) {
		((CardapioAppGarcom) getApplication()).setQtdMesa(qtdMesa);
	}

	public List<Long> getFiltroMesa() {
		return ((CardapioAppGarcom) getApplication()).getFiltroMesa();
	}

	public void setFiltroMesa(List<Long> filtroMesa) {
		((CardapioAppGarcom) getApplication()).setFiltroMesa(filtroMesa);
	}

	public Usuario getUsuario() {
		return ((CardapioAppGarcom) getApplication()).getUsuarioLogado();
	}

	public void setUsuario(Usuario usuario) {
		((CardapioAppGarcom) getApplication()).setUsuarioLogado(usuario);
	}
}
