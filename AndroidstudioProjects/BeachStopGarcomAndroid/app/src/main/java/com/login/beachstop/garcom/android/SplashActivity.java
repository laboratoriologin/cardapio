package com.login.beachstop.garcom.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.login.beachstop.garcom.android.models.Acao;
import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.Empresa;
import com.login.beachstop.garcom.android.models.Item;
import com.login.beachstop.garcom.android.models.Kit;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.SubItem;
import com.login.beachstop.garcom.android.network.AcaoRequest;
import com.login.beachstop.garcom.android.network.CategoriaRequest;
import com.login.beachstop.garcom.android.network.EmpresaRequest;
import com.login.beachstop.garcom.android.network.ItemRequest;
import com.login.beachstop.garcom.android.network.KitRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Argus on 16/12/2014.
 */
public class SplashActivity extends DefaultActivity {

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
                                SplashActivity.this.getDataManager().getKitDAO().deleteAll();
                                SplashActivity.this.getDataManager().getKitSubItemDAO().deleteAll();
                                SplashActivity.this.getDataManager().getEmpresaDAO().deleteAll();
                                SplashActivity.this.getDataManager().getSetorDAO().deleteAll();

                                getDataManager().getEmpresaDAO().save((Empresa) serverResponse.getReturnObject());

                                new CategoriaRequest(responseGetCategoria).getAtivo();
                            }
                        } else {
                            getDataManager().getEmpresaDAO().save((Empresa) serverResponse.getReturnObject());
                            new CategoriaRequest(responseGetCategoria).getAtivo();
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

    @SuppressWarnings("unchecked")
    private ResponseListener responseGetCategoria = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        configCategoria((List<Categoria>) serverResponse.getReturnObject());
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

    private ResponseListener responseListenerGetAcao = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        getDataManager().getAcaoDAO().save((List<Acao>) serverResponse.getReturnObject());
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

    private ResponseListener responseGetItem = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        SplashActivity.this.getDataManager().getItemDAO().save((List<Item>) serverResponse.getReturnObject());
                        loadSubItem((List<Item>) serverResponse.getReturnObject());

                        if (getDataManager().getKitDAO().getQtd() == 0)
                            new KitRequest(responseGetKit).get(new Kit());
                        else
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

    private ResponseListener responseGetKit = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        SplashActivity.this.getDataManager().getKitDAO().save((List<Kit>) serverResponse.getReturnObject());
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

        int SPLASH_MILIS = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                textView.setVisibility(TextView.VISIBLE);
                textView.setText("Atualizando informações do sistema");
                new EmpresaRequest(responseGetEmpresa).getKeyCardapio();
                if(getDataManager().getAcaoDAO().qtdAll() == 0) {
                    new AcaoRequest(responseListenerGetAcao).get(new Acao());
                }
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
            new ItemRequest(responseGetItem).getItemByCategorias(listItemByCardapio);
        else if (this.getDataManager().getKitDAO().getQtd() == 0)
            new KitRequest(responseGetKit).get(new Kit());
        else {
            loadSubItem(null);
            goCardapio();
        }
    }

    //Colocar todos os subItens na memória do celular, pois busca do banco de dados está demorando muito
    //TODO: Melhorar lógica, retirar os loops
    public void loadSubItem(List<Item> itens) {
        if (itens == null)
            itens = getDataManager().getItemDAO().getAll();

        if (getSubItens() == null) {
            setSubItens(new ArrayList<SubItem>());
            for (Item item : itens) {
                for (SubItem subItem : item.getSubItens()) {
                    subItem.setItem(item);
                }
                getSubItens().addAll(item.getSubItens());
            }
        }

        Collections.sort(getSubItens());
    }

    public void goCardapio() {

        loadSubItem(null);

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

    private void configCategoria(List<Categoria> categorias) {

        for (Categoria categoria : categorias) {
            categoria.setTipoCategoria(Constantes.TipoCategoriaCardapio.ITEM);
        }

        Categoria categoria = new Categoria();
        categoria.setId(98l);
        categoria.setTipoCategoria(Constantes.TipoCategoriaCardapio.KIT);
        categoria.setArea(1l);
        categoria.setDescricao("Kit");
        categoria.setFlagAtivo(true);
        categoria.setOrdem((long) (categorias.size() + 1));
        categoria.setImagem("");
        categoria.setResourceImg(R.drawable.bt_home_kit);

        categorias.add(categoria);
    }
}
