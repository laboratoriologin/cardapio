package com.login.beachstop.garcom.android;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.IntentCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.login.beachstop.garcom.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.SubItem;
import com.login.beachstop.garcom.android.models.Usuario;
import com.login.beachstop.garcom.android.network.PausaRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import java.util.List;


/**
 * Created by Argus on 16/12/2014.
 */
public class DefaultActivity extends FragmentActivity {

    private final int SPLASH_MILIS_PAUSA = 1500;
    private ProgressDialog progressDialogPausa;
    private Menu menu;

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
            this.menu = menu;

//            if(getDataManager().getPausaDAO().hasPausa()){
//                MenuItem item = menu.findItem(R.id.menu_pausa);
//                item.setChecked(true);
//                item.setTitle("Fechar Pausa");
//            }
            return true;
        } else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
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
            case R.id.menu_pausa:
                if (!item.isChecked())
                    abrirPausa(item);
//                else
//                    fecharPausa(item);
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

    public void abrirPausa(final MenuItem item) {
        progressDialogPausa = new ProgressDialog(this);
        progressDialogPausa.setMessage("Processando...");
        progressDialogPausa.show();
        progressDialogPausa.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialogPausa.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        new PausaRequest(new ResponseListener() {
            @Override
            public void onResult(ServerResponse serverResponse) {
                if (serverResponse != null) {
                    if (serverResponse.isOK()) {
                        try {
                            progressDialogPausa.setMessage("Processo realizado com sucesso...");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialogPausa.dismiss();
                                    Intent intent = new Intent(DefaultActivity.this, FecharPausaActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }, SPLASH_MILIS_PAUSA);

                        } catch (Exception e) {
                            progressDialogPausa.setMessage(Constantes.MSG_ERRO_GRAVAR_DADOS);
                        }
                    } else {
                        progressDialogPausa.setMessage(serverResponse.getMsgErro());
                    }
                } else {
                    progressDialogPausa.setMessage("Falha no processamento...");
                }
            }
        }).abrirPausa(getUsuario());
    }

//    private void fecharPausa(final MenuItem item) {
//        progressDialogPausa = new ProgressDialog(this);
//        progressDialogPausa.setMessage("Processando...");
//        progressDialogPausa.show();
//        progressDialogPausa.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        progressDialogPausa.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//
//        Pausa pausa = getDataManager().getPausaDAO().get();
//
//        new PausaRequest(new ResponseListener() {
//            @Override
//            public void onResult(ServerResponse serverResponse) {
//                if (serverResponse != null) {
//                    if (serverResponse.isOK()) {
//                        try {
//                            getDataManager().getPausaDAO().deleteAll();
//                            item.setChecked(false);
//                            item.setTitle("Abrir Pausa");
//                            progressDialogPausa.setMessage("Processo realizado com sucesso...");
//                        } catch (Exception e) {
//                            progressDialogPausa.setMessage(Constantes.MSG_ERRO_GRAVAR_DADOS);
//                        }
//                    } else {
//                        progressDialogPausa.setMessage(serverResponse.getMsgErro());
//                    }
//                } else {
//                    progressDialogPausa.setMessage("Falha no processamento...");
//                }
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialogPausa.dismiss();
//                    }
//                }, SPLASH_MILIS_PAUSA);
//            }
//        }).fecharPausa(pausa);
//    }
}
