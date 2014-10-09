package com.login.android.cardapio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.login.android.cardapio.business.BusinessResult;
import com.login.android.cardapio.business.ContaBS;
import com.login.android.cardapio.model.Conta;
import com.login.android.cardapio.model.ServerResponse;
import com.login.android.cardapio.util.Constantes;
import com.login.android.cardapio.view.ActionBar;

public class CheckInActivity extends DefaultActivity implements BusinessResult {

	private Conta conta;

	@Override
	protected void onResume() {
		super.onResume();
		if (this.conta == null) {
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);

		this.conta = this.getDataManager().getConta();

		if (this.conta != null) {
			((TextView) findViewById(R.id.activity_checkin_text_view_mesa)).setText(this.conta.getMesa().toString());
			((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setText("Trocar de  mesa.");
		}

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);
		((ImageView) (findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar)).setBackgroundResource(R.drawable.tab_check_in);
		((TextView) (findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Check-In");

		((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(CheckInActivity.this);
				integrator.initiateScan();
			}
		});

	}

	boolean isDigit(String s) {
		return s.matches("[0-9]*");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (result != null) {
			String contents = result.getContents();

			if (contents != null) {

				try {
					String meseSelecionada = contents.split("&")[0].split("=")[1];

					if (isDigit(meseSelecionada) && (Long.valueOf(meseSelecionada) > 0 && Long.valueOf(meseSelecionada) <= getQtdMesa())) {

						this.conta = this.getDataManager().getConta();

						if (this.conta != null) {
							this.conta.setMesa(Long.valueOf(meseSelecionada));
						} else {
							this.conta = new Conta(Long.valueOf(meseSelecionada));
						}

						new ContaBS(this).getContaMesa(this.conta);
						((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setVisibility(Button.GONE);
						((ProgressBar) findViewById(R.id.activity_checkin_progres_bar)).setVisibility(ProgressBar.VISIBLE);
						((TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg)).setVisibility(TextView.VISIBLE);

						((TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg)).setText("Analisando as condições da mesa!");
					} else {
						Toast.makeText(this, "Erro no Qr Code!", Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					Toast.makeText(this, "Erro no Qr Code!", Toast.LENGTH_SHORT).show();
				}

			} else {
				this.conta = new Conta(-1l);
			}
		}
	}

	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;
		this.conta = (Conta) serverResponse.getReturnObject();

		((ProgressBar) findViewById(R.id.activity_checkin_progres_bar)).setVisibility(ProgressBar.GONE);
		((TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg)).setVisibility(TextView.GONE);
		((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setVisibility(Button.VISIBLE);

		if (serverResponse.isOK()) {
			try {

				if (this.getDataManager().getConta() != null) {
					this.getDataManager().getContaDAO().update(this.conta, this.conta.getId());
				} else {
					this.getDataManager().getContaDAO().save(this.conta);
				}

				((TextView) findViewById(R.id.activity_checkin_text_view_mesa)).setText(this.conta.getMesa().toString());
				((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setText("Trocar de  mesa.");
			} catch (Exception e) {
				Toast.makeText(this, Constantes.MSG_ERRO_READ_QR_CODE, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
		}
	}
}
