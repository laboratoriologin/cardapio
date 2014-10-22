package com.login.beachstop.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.util.Utilitarios;
import com.login.beachstop.android.view.ActionBar;

public class CheckInActivity extends DefaultActivity implements BusinessResult {

	private Conta conta;
	private SocialAuthAdapter socialAuthAdapter;

	@Override
	protected void onResume() {
		super.onResume();
		if (this.conta == null) {
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.addExtra("SAVE_HISTORY", false);
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
			// ((Button)
			// findViewById(R.id.activity_checkin_text_view_qr_code)).setText("Trocar de  mesa.");
			((ProgressBar) findViewById(R.id.activity_checkin_progres_bar)).setVisibility(ProgressBar.GONE);
			((TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg)).setVisibility(TextView.GONE);
			((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setVisibility(Button.GONE);
			((Button) findViewById(R.id.activity_checkin_share)).setVisibility(Button.VISIBLE);
		}

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);
		((ImageView) (findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar)).setBackgroundResource(R.drawable.tab_check_in);
		((TextView) (findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Check-In");

		((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(CheckInActivity.this);
				integrator.addExtra("SAVE_HISTORY", false);
				integrator.initiateScan();
			}
		});

		socialAuthAdapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
		socialAuthAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);

		socialAuthAdapter.enable(((Button) findViewById(R.id.activity_checkin_share)));

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

						if (this.conta == null) {

							String horaAtual = Utilitarios.getHourNow();
							this.conta = new Conta(Long.valueOf(meseSelecionada));
							this.conta.setId(Long.valueOf(horaAtual));
							this.conta.setHorarioChegada(horaAtual);
							this.conta.setListPedido(new ArrayList<Pedido>());
							this.conta.setIsShare(false);
							this.conta.setValor("0");
							this.conta.setValorPago("0");

							this.getDataManager().getContaDAO().save(this.conta);

							((ProgressBar) findViewById(R.id.activity_checkin_progres_bar)).setVisibility(ProgressBar.GONE);
							((TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg)).setVisibility(TextView.GONE);
							((Button) findViewById(R.id.activity_checkin_text_view_qr_code)).setVisibility(Button.GONE);
							((Button) findViewById(R.id.activity_checkin_share)).setVisibility(Button.VISIBLE);

							((TextView) findViewById(R.id.activity_checkin_text_view_mesa)).setText(this.conta.getMesa().toString());

						}

						// new ContaBS(this).getContaMesa(this.conta);
						// ((Button)
						// findViewById(R.id.activity_checkin_text_view_qr_code)).setVisibility(Button.GONE);
						// ((ProgressBar)
						// findViewById(R.id.activity_checkin_progres_bar)).setVisibility(ProgressBar.VISIBLE);
						// ((TextView)
						// findViewById(R.id.activity_checkin_text_view_lbl_msg)).setVisibility(TextView.VISIBLE);

						// ((TextView)
						// findViewById(R.id.activity_checkin_text_view_lbl_msg)).setText("Analisando as condições da mesa!");
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
		if (result != null) {
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
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
		}
	}

	private final class ResponseListener implements DialogListener {
		public void onComplete(Bundle values) {

			try {
				socialAuthAdapter.updateStatus("No Beach Stop Ipitanga!", new MessageListener(), true);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(CheckInActivity.this, "Erro ao compartilhar na rede social. Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
			}
		}

		public void onCancel() {
			Log.d("ShareButton", "Cancelado");
		}

		@Override
		public void onBack() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(SocialAuthError arg0) {
			Log.d("ShareButton", "Cancelado");

		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {

		public void onError(SocialAuthError e) {
			System.out.println(e.getMessage());
		}

		@Override
		public void onExecute(String arg0, Integer status) {
			// TODO Auto-generated method stubInteger status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(CheckInActivity.this, "Compartilhado!", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(CheckInActivity.this, "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();

		}
	}

}
