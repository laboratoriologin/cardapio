package com.login.android.cardapio.garcom;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.cardapio.garcom.business.BusinessResult;
import com.login.android.cardapio.garcom.business.UsuarioBS;
import com.login.android.cardapio.garcom.model.Usuario;
import com.login.android.cardapio.garcom.util.Constantes;
import com.login.android.cardapio.garcom.util.Utilitarios;

public class LembraLoginActivity extends DefaultActivity implements BusinessResult {

	private Usuario usuarioPesquisado;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_lembra);

	}

	public void enviarEmail(View view) {
		
		if (TextUtils.isEmpty(((TextView) findViewById(R.id.edtEmail)).getText())) {
		
			Toast.makeText(LembraLoginActivity.this, "Preencha o campo!", Toast.LENGTH_SHORT).show();
		
		} else {

			usuarioPesquisado = new Usuario();

			usuarioPesquisado.setEmail(((TextView) findViewById(R.id.edtEmail)).getText().toString());

			if (Utilitarios.hasConnection(this)) {
				
				new UsuarioBS(this).enviarEmail(usuarioPesquisado);

				progressDialog = new ProgressDialog(this);
				progressDialog.setMessage("Enviando nova senha para o seu e-mail...");
				progressDialog.show();
				progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
			
			} else {
			
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			
			}

		}
	}

	public void getBusinessResult(Object result) {

		progressDialog.dismiss();
	}

}