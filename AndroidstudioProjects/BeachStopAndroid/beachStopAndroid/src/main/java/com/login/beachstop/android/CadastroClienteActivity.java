package com.login.beachstop.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.login.beachstop.android.models.Cliente;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.ClienteRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

import static com.login.beachstop.android.R.layout.activity_cadastro_cliente;

/**
 * Created by Argus on 27/10/2014.
 */
public class CadastroClienteActivity extends DefaultActivity {

    private final int SPLASH_MILIS = 1000;
    protected EditText editTextNome;
    protected EditText editTextEmail;
    protected EditText editTextCelular;
    protected EditText editTextAniversario;
    private ProgressDialog progressDialog;
    private Cliente cliente;
    private ResponseListener listenerCadastroCliente = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    Cliente clienteResponse = (Cliente) serverResponse.getReturnObject();
                    cliente.setId(clienteResponse.getId());
                    cliente.setDataNascimento(clienteResponse.getDataNascimento());
                    progressDialog.setMessage("Você foi cadastrado com sucesso em nosso sistemas. Aproveite!");

                    try {

                        getDataManager().getClienteDAO().save(cliente);

                    } catch (Exception e) {

                        e.printStackTrace();
                        progressDialog.setMessage("Ops! Ocorreu um erro no sistema ao grava os dados no seu celular.");

                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();
                            goToStart();

                        }
                    }, SPLASH_MILIS);

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(CadastroClienteActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                progressDialog.dismiss();
                Toast.makeText(CadastroClienteActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_cadastro_cliente);

        this.editTextNome = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_nome);
        this.editTextEmail = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_email);
        this.editTextCelular = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_celular);
        this.editTextAniversario = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_data_nascimento);


    }

    public void salvar(View view) {

        boolean isOK = true;

        if ("".equals(this.editTextNome.getText().toString())) {
            Toast.makeText(this, "Digite seu nome!", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if ("".equals(this.editTextEmail.getText().toString())) {
            Toast.makeText(this, "Digite seu email!", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if ("".equals(this.editTextCelular.getText().toString())) {
            Toast.makeText(this, "Digite seu celular!", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if (isOK) {

            this.cliente = new Cliente();
            this.cliente.setNome(this.editTextNome.getText().toString());
            this.cliente.setEmail(this.editTextEmail.getText().toString());
            this.cliente.setCelular(this.editTextCelular.getText().toString());
            this.cliente.setDataNascimento(this.editTextAniversario.getText().toString());
            this.cliente.setToken("");

            new ClienteRequest(this.listenerCadastroCliente).post(cliente);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Enviando os dados para os nossos servidores...");
            progressDialog.show();
            progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    public void cancelar(View view) {

        goToStart();

    }

    private void goToStart() {

        Intent mainIntent = new Intent(CadastroClienteActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(mainIntent);
        this.finish();

    }

}
