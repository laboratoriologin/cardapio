package com.login.beachstop.android.garcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.garcom.business.BusinessResult;
import com.login.beachstop.android.garcom.business.UsuarioBS;
import com.login.beachstop.android.garcom.model.Empresa;
import com.login.beachstop.android.garcom.model.ServerResponse;
import com.login.beachstop.android.garcom.model.Usuario;
import com.login.beachstop.android.garcom.util.Constantes;
import com.login.beachstop.android.garcom.util.Utilitarios;

public class LoginActivity extends DefaultActivity implements BusinessResult {
	private ProgressDialog progressDialog;
	private Usuario usuarioPesquisado;
	private Empresa empresaPesquisado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

	}

	public void autenticar(View view) throws Exception {
		if (TextUtils.isEmpty(((TextView) findViewById(R.id.edtLogin)).getText()) || TextUtils.isEmpty(((TextView) findViewById(R.id.edtSenha)).getText())) {

			Toast.makeText(LoginActivity.this, "Preencha os dois campos!", Toast.LENGTH_SHORT).show();

		} else {

			usuarioPesquisado = new Usuario();

			empresaPesquisado = new Empresa();

			empresaPesquisado.setKeyMobile(Constantes.KEYMOBILE);

			usuarioPesquisado.setLogin(((TextView) findViewById(R.id.edtLogin)).getText().toString());

			usuarioPesquisado.setSenha(((TextView) findViewById(R.id.edtSenha)).getText().toString());

			if (Utilitarios.hasConnection(this)) {

				progressDialog = new ProgressDialog(this);

				progressDialog.setMessage("Autenticando...");

				progressDialog.show();
				progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

				progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

				new UsuarioBS(this).logar(usuarioPesquisado);

			} else {
				Toast.makeText(this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void getBusinessResult(Object result) {

		progressDialog.dismiss();
		ServerResponse srRetorno = (ServerResponse) result;
		if (srRetorno.isOK()) {
			this.setUsuario((Usuario) srRetorno.getReturnObject());
			this.goCardapio();
		} else {
			Toast.makeText(LoginActivity.this, Constantes.MSG_ERRO_LOGAR, Toast.LENGTH_SHORT).show();
		}
	}

	public void goCardapio() {

		Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);

		LoginActivity.this.startActivity(mainIntent);
		LoginActivity.this.finish();
	}

	public void lembrarSenha(View view) {

		Intent mainIntent = new Intent(LoginActivity.this, LembraLoginActivity.class);

		LoginActivity.this.startActivity(mainIntent);
		LoginActivity.this.finish();
	}

}