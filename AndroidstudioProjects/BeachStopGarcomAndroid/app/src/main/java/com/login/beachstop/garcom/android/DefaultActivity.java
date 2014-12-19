package com.login.beachstop.garcom.android;


import android.view.View;

import com.login.beachstop.garcom.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.garcom.android.models.Categoria;
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
}
