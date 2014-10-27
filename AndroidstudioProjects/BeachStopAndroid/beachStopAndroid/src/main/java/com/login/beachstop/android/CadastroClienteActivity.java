package com.login.beachstop.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.login.beachstop.android.R.layout.activity_cadastro_cliente;

/**
 * Created by Argus on 27/10/2014.
 */
public class CadastroClienteActivity extends DefaultActivity {

    protected EditText editTextNome;
    protected EditText editTextemail;
    protected EditText editTextCelular;
    protected EditText editTextAniversario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_cadastro_cliente);

        this.editTextNome = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_nome);
        this.editTextemail = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_email);
        this.editTextCelular = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_celular);
        this.editTextAniversario = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_data_nascimento);


    }

    public void cancelar(View view) {

//        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        SplashActivity.this.startActivity(mainIntent);
//        SplashActivity.this.finish();

    }

}
