package com.login.beachstop.garcom.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
public class LoginActivity extends DefaultActivity {

    private ProgressDialog progressDialog;
    private Usuario usuario;
    private TextView textViewUsuario;
    private TextView textViewSenha;
    private ResponseListener ResponseListenerAutenticacao = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    LoginActivity.this.setUsuario((Usuario) serverResponse.getReturnObject());
                    goCardapio();
                } else {
                    progressDialog.setMessage(Constantes.MSG_ERRO_LOGAR);
                }
            } else {
                progressDialog.setMessage(Constantes.MSG_ERRO_NET);
            }
            progressDialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void autenticar(View view) {
        this.textViewUsuario = (TextView) findViewById(R.id.activity_login_edit_text_login);
        this.textViewSenha = (TextView) findViewById(R.id.activity_login_edit_text_password);

        if (TextUtils.isEmpty(this.textViewUsuario.getText()) || TextUtils.isEmpty(textViewSenha.getText())) {
            Toast.makeText(this, "Preencha os dois campos!", Toast.LENGTH_SHORT).show();
        } else {
            usuario = new Usuario();
            usuario.setLogin(this.textViewUsuario.getText().toString());
            usuario.setSenha(this.textViewSenha.getText().toString());

            if (Utilitarios.hasConnection(this)) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Autenticando...");
                progressDialog.show();
                progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                new UsuarioRequest(ResponseListenerAutenticacao).logar(usuario);
            } else {
                Toast.makeText(this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void lembrarSenha(View view) {
        Intent mainIntent = new Intent(LoginActivity.this, LoginLembrarActivity.class);
        LoginActivity.this.startActivity(mainIntent);
    }

    public void goCardapio() {
        Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(mainIntent);
        LoginActivity.this.finish();
    }
}
