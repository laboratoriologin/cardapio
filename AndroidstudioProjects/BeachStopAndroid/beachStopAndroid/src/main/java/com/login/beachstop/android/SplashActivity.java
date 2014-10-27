package com.login.beachstop.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Empresa;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.CategoriaRequest;
import com.login.beachstop.android.network.EmpresaRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

import java.util.List;

public class SplashActivity extends DefaultActivity {

    private final int SPLASH_MILIS = 1000;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageButton imageButton;
    private ResponseListener responseCategorias = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        getDataManager().getCategoriaDAO().save((List<Categoria>) serverResponse.getReturnObject());

                        verificarClienteCadastrado();

                    } catch (Exception e) {
                        e.printStackTrace();
                        setStatusApresentacao(false, true, Constantes.MSG_ERRO_GRAVAR_DADOS, true);
                    }


                } else {
                    setStatusApresentacao(false, true, serverResponse.getMsgErro(), true);
                }


            } else {

                setStatusApresentacao(false, true, Constantes.MSG_ERRO_NET, true);

            }

        }
    };

    private ResponseListener responseKeyCardapio = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        Empresa empresa = (Empresa) serverResponse.getReturnObject();

                        if (getKeyCardapio() == null || !getKeyCardapio().equals(empresa.getKeyCardapio())) {

                            setKeyCardapio(empresa.getKeyCardapio());


                            if (getDataManager().getCategoriaDAO().deleteAll() && getDataManager().getKitDAO().deleteAll()) {

                                new CategoriaRequest(responseCategorias).getAtivo();

                            } else {

                                setStatusApresentacao(false, true, Constantes.MSG_ERRO_GRAVAR_DADOS, true);

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        setStatusApresentacao(false, true, Constantes.MSG_ERRO_GRAVAR_DADOS, true);
                    }


                } else {
                    setStatusApresentacao(false, true, serverResponse.getMsgErro(), true);
                }


            } else {

                setStatusApresentacao(false, true, Constantes.MSG_ERRO_NET, true);

            }
        }
    };

    private void verificarClienteCadastrado() {

        if (!getDataManager().getClienteDAO().hasCliente()) {
            goToCadastroCliente();
        } else {
            goCardapio();
        }

    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        this.progressBar = (ProgressBar) this.findViewById(R.id.activity_splash_progressBar);
        this.textView = (TextView) this.findViewById(R.id.activity_splash_textView);
        this.imageButton = (ImageButton) this.findViewById(R.id.activity_splash_image_button);

        this.imageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startDados();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startDados();
            }
        }, SPLASH_MILIS);

    }

    private void setStatusApresentacao(boolean isVisibleProgressBar, boolean isVisibleTextView, String textApresentacao, boolean isVisibleImageButton) {

        if (isVisibleProgressBar) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        } else {
            progressBar.setVisibility(ProgressBar.GONE);
        }

        if (isVisibleTextView) {
            textView.setVisibility(TextView.VISIBLE);
            textView.setText(textApresentacao);
        } else {
            textView.setVisibility(TextView.GONE);
        }

        if (isVisibleImageButton) {
            imageButton.setVisibility(ImageButton.VISIBLE);
        } else {
            imageButton.setVisibility(ImageButton.GONE);
        }
    }

    private void startDados() {

        setStatusApresentacao(true, true, Constantes.MSG_SAUDACAO_DOIS, false);


        if (getDataManager().getCategoriaDAO().getQtdCategoria() == 0) {

            new CategoriaRequest(responseCategorias).getAtivo();

        } else {

            new EmpresaRequest(responseKeyCardapio).getKeyCardapio();

        }

    }

    private void goCardapio() {

//        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//        SplashActivity.this.startActivity(mainIntent);
//        SplashActivity.this.finish();

    }

    private void goToCadastroCliente() {

        Intent mainIntent = new Intent(SplashActivity.this, CadastroClienteActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(mainIntent);
        this.finish();

    }


}
