package com.login.beachstop.garcom.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.Empresa;
import com.login.beachstop.garcom.android.models.Item;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.CategoriaRequest;
import com.login.beachstop.garcom.android.network.EmpresaRequest;
import com.login.beachstop.garcom.android.network.ItemRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 16/12/2014.
 */
public class SplashActivity extends DefaultActivity {

    private final int SPLASH_MILIS = 1000;
    private ProgressBar progressBar;
    private TextView textView;
    private ResponseListener responseGetEmpresa = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {

                        if (SplashActivity.this.getDataManager().getEmpresaDAO().hasEmpresa()) {

                            Empresa empresa = SplashActivity.this.getDataManager().getEmpresaDAO().get();

                            if (empresa.getKeyCardapio().equals(((Empresa) serverResponse.getReturnObject()).getKeyCardapio())) {
                                SplashActivity.this.setCategorias(SplashActivity.this.getDataManager().getCategoriaDAO().getAllOrderByOrdem());
                                goCardapio();
                            } else {
                                SplashActivity.this.getDataManager().getCategoriaDAO().deleteAll();
                                SplashActivity.this.getDataManager().getItemDAO().deleteAll();
                                SplashActivity.this.getDataManager().getSubItemDAO().deleteAll();

                                new CategoriaRequest(reponseGetCategoria).getAtivo();
                            }
                        } else {
                            new CategoriaRequest(reponseGetCategoria).getAtivo();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
                    }
                } else {
                    textView.setText(serverResponse.getMsgErro());
                }
            } else {
                textView.setText(Constantes.MSG_ERRO_NET);
            }
        }
    };

    private ResponseListener reponseGetCategoria = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        SplashActivity.this.setCategorias((List<Categoria>) serverResponse.getReturnObject());
                        SplashActivity.this.getDataManager().getCategoriaDAO().save(SplashActivity.this.getCategorias());
                        getItemByCardapio();
                    } catch (Exception e) {
                        e.printStackTrace();
                        textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
                    }
                } else {
                    textView.setText(serverResponse.getMsgErro());
                }
            } else {
                textView.setText(Constantes.MSG_ERRO_NET);
            }
        }
    };

    private ResponseListener reponseGetItem = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        SplashActivity.this.getDataManager().getItemDAO().save((List<Item>) serverResponse.getReturnObject());
                        goCardapio();
                    } catch (Exception e) {
                        e.printStackTrace();
                        textView.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
                    }
                } else {
                    textView.setText(serverResponse.getMsgErro());
                }
            } else {
                textView.setText(Constantes.MSG_ERRO_NET);
            }
        }
    };

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
                new EmpresaRequest(responseGetEmpresa).getKeyCardapio();
            }
        }, SPLASH_MILIS);

    }

    private void getItemByCardapio() {

        List<Long> listItemByCardapio = new ArrayList<Long>();

        for (Categoria categoria : this.getDataManager().getCategoriaDAO().getAllOrderByOrdem()) {
            if (this.getDataManager().getItemDAO().getQtdItem(categoria) == 0)
                listItemByCardapio.add(categoria.getId());
        }

        if (listItemByCardapio.size() != 0)
            new ItemRequest(reponseGetItem).getItemByCategorias(listItemByCardapio);
        else
            goCardapio();

    }

    public void goCardapio() {

        if (this.getUsuario() == null) {
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            this.startActivity(mainIntent);
            this.finish();
        } else {
            Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
            this.startActivity(mainIntent);
            this.finish();
        }
    }
}
