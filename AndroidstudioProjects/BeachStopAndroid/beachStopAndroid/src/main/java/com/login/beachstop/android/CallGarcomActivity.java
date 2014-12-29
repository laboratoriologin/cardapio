package com.login.beachstop.android;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.business.AlertaBS;
import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.view.ActionBar;

public class CallGarcomActivity extends DefaultActivity implements BusinessResult {

	private Button btnChamarGarcom;
	private Button btnFecharConta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_garcom);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);
		((ImageView) (findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar)).setBackgroundResource(R.drawable.bt_chamar_garcom);
		((TextView) (findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Chamar garçom");

		this.btnChamarGarcom = (Button) findViewById(R.id.activity_call_garcom_button_call_garcom);
		this.btnFecharConta = (Button) findViewById(R.id.tab_fragment_conta_button_enviar_conta);

		this.btnChamarGarcom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getDataManager().getConta() != null) {
					new AlertaBS(CallGarcomActivity.this).callWaiter(getDataManager().getConta());
				} else {
					Toast.makeText(CallGarcomActivity.this, "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	public void getBusinessResult(Object result) {
		if (result != null) {
			ServerResponse serverResponse = (ServerResponse) result;

			if (serverResponse.isOK()) {
				Toast.makeText(this, "Uma Garçom irá lhe atender em breve! Divirta-se!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
		}

	}
}
