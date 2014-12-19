package com.login.beachstop.garcom.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Usuario;
import com.login.beachstop.garcom.android.network.UsuarioRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.utils.Utilitarios;

/**
 * Created by Argus on 18/12/2014.
 */
public class LoginLembrarActivity extends DefaultActivity {

    private final int SPLASH_MILIS = 1500;
    private ResponseListener responseListenerEnviarEmail = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        progressDialog.setMessage("Solicitação em andamento, verifique seu e-mail!");
                    } catch (Exception e) {
                        progressDialog.setMessage(Constantes.MSG_ERRO_GRAVAR_DADOS);
                    }
                } else {
                    progressDialog.setMessage(serverResponse.getMsgErro());
                }
            } else {
                progressDialog.setMessage(Constantes.MSG_ERRO_NET);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, SPLASH_MILIS);

        }
    };
    private Usuario usuarioPesquisado;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_lembrar);

    }

    public void enviarEmail(View view) {

        String email = ((TextView) findViewById(R.id.activity_login_lembrar_senha_edit_text_email)).getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginLembrarActivity.this, "Preencha o campo!", Toast.LENGTH_SHORT).show();
        } else {
            usuarioPesquisado = new Usuario();
            usuarioPesquisado.setEmail(email);

            if (Utilitarios.hasConnection(this)) {

                new UsuarioRequest(responseListenerEnviarEmail).LembrarSenha(usuarioPesquisado);

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Enviando nova senha para o seu e-mail...");
                progressDialog.show();
                progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            } else {
                Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
