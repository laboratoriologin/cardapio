package com.login.beachstop.garcom.android;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.login.beachstop.garcom.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.SubItem;
import com.login.beachstop.garcom.android.models.Usuario;

import java.util.List;


/**
 * Created by Argus on 16/12/2014.
 */
public class DefaultActivity extends FragmentActivity {

    public DataManager getDataManager() {
        return ((CardapioGarcomApp) getApplication()).getDataManager();
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

    public Long getQtdMesa() {
        return ((CardapioGarcomApp) getApplication()).getQtdMesa();
    }

    public void setQtdMesa(Long qtdMesa) {
        ((CardapioGarcomApp) getApplication()).setQtdMesa(qtdMesa);
    }

    public List<Long> getFiltroMesa() {
        return ((CardapioGarcomApp) getApplication()).getFiltroMesa();
    }

    public void setFiltroMesa(List<Long> filtroMesa) {
        ((CardapioGarcomApp) getApplication()).setFiltroMesa(filtroMesa);
    }

    public Usuario getUsuario() {
        return ((CardapioGarcomApp) getApplication()).getUsuarioLogado();
    }

    public void setUsuario(Usuario usuario) {
        ((CardapioGarcomApp) getApplication()).setUsuarioLogado(usuario);
    }

    public List<Categoria> getCategorias() {
        return ((CardapioGarcomApp) getApplication()).getCategorias();
    }

    public void setCategorias(List<Categoria> categorias) {
        ((CardapioGarcomApp) getApplication()).setCategorias(categorias);
    }

    public List<SubItem> getSubItens() {
        return ((CardapioGarcomApp) getApplication()).getSubItems();
    }

    public void setSubItens(List<SubItem> subItens) {
        ((CardapioGarcomApp) getApplication()).setSubItems(subItens);
    }
}
