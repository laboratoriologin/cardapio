package com.login.beachstop.garcom.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.login.beachstop.garcom.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Argus on 16/12/2014.
 */
public class CardapioGarcomApp extends Application {

    private DataManager dataManager;
    private SharedPreferences settings;
    private List<Categoria> categorias;

    private String PREFS_NAME = "settings";
    private String QTD_MESA = "qtd_mesa";
    private String FILTRO_MESA = "filtro_mesa";
    private String USUARIO_ID = "usuario_id";
    private String USUARIO_NOME = "usuario_nome";

    @Override
    public void onCreate() {
        super.onCreate();
        setDataManager(new DataManager(this));
        this.settings = getSharedPreferences(PREFS_NAME, 0);

    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Long> getFiltroMesa() {

        String mesas = settings.getString(FILTRO_MESA, null);

        List<Long> mesasRetorno = new ArrayList<Long>();

        if (!TextUtils.isEmpty(mesas)) {

            for (String mesa : mesas.split(",")) {

                mesasRetorno.add(Long.valueOf(mesa));

            }

        }

        return mesasRetorno;

    }

    public void setFiltroMesa(List<Long> filtroMesa) {

        Editor editor = settings.edit();

        if (filtroMesa == null) {

            editor.remove(FILTRO_MESA);

        } else {

            StringBuilder mesas = new StringBuilder();

            Iterator<Long> iterator = filtroMesa.iterator();

            while (iterator.hasNext()) {

                mesas.append(iterator.next());

                if (iterator.hasNext()) {
                    mesas.append(",");
                }

            }

            editor.putString(FILTRO_MESA, mesas.toString());

        }

        editor.commit();
    }

    public Long getQtdMesa() {
        return settings.getLong(QTD_MESA, 0L);
    }

    public void setQtdMesa(Long qtdMesa) {

        Editor editor = settings.edit();

        editor.putLong(QTD_MESA, qtdMesa);

        editor.commit();

    }

    /**
     * @return the usuarioLogado
     */
    public Usuario getUsuarioLogado() {

        Usuario usuario = new Usuario();

        usuario.setId(settings.getLong(USUARIO_ID, 0L));

        usuario.setNome(settings.getString(USUARIO_NOME, ""));

        usuario.setMesas(this.getFiltroMesa());

        if (usuario.getId().intValue() == 0) {
            return null;
        }

        return usuario;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {

        Editor editor = settings.edit();

        if (usuarioLogado == null) {

            editor.remove(USUARIO_ID);

            editor.remove(USUARIO_NOME);

            setFiltroMesa(null);

            editor.commit();

        } else {

            editor.putLong(USUARIO_ID, usuarioLogado.getId());

            editor.putString(USUARIO_NOME, usuarioLogado.getNome());

            editor.commit();

            this.setFiltroMesa(usuarioLogado.getMesas());

        }

    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
