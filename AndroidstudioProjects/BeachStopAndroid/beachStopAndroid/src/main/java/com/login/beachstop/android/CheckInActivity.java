package com.login.beachstop.android;

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
import com.login.beachstop.android.models.Cliente;
import com.login.beachstop.android.models.Conta;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.ContaRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.Utilitarios;
import com.login.beachstop.android.views.actionbar.ActionBar;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import static com.login.beachstop.android.R.layout.activity_checkin;


public class CheckInActivity extends DefaultActivity {

    protected ProgressBar progressBar;
    protected TextView textViewMsg;
    protected TextView textViewNumero;
    protected Button buttonQrCode;
    protected Button buttonShared;
    protected ActionBar actionBar;
    protected ImageView imageViewActionBar;
    protected TextView textViewActionBar;

    private Conta conta;
    private ResponseListener listenerGetConta = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    Long contaId = ((Conta) serverResponse.getReturnObject()).getId();

                    changeStatusView(false, false, false, false);

                    try {

                        conta.setId(contaId);

                        if (getDataManager().getContaDAO().get() != null) {

                            getDataManager().getContaDAO().update(conta, conta.getId());

                        } else {

                            getDataManager().getContaDAO().save(conta);

                        }

                        textViewNumero.setText(conta.getNumero().toString());

                    } catch (Exception e) {

                        Toast.makeText(CheckInActivity.this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }


                } else {

                    Toast.makeText(CheckInActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                Toast.makeText(CheckInActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }
    };
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
        setContentView(activity_checkin);

        loadViews();

        this.conta = this.getDataManager().getContaDAO().get();

        if (this.conta != null) {

            this.textViewNumero.setText(this.conta.getNumero().toString());
            this.changeStatusView(false, false, false, true);
        }

        this.actionBar.setDisplayHomeAsUpEnabled(Boolean.FALSE);
        this.imageViewActionBar.setBackgroundResource(R.drawable.tab_check_in);
        this.textViewActionBar.setText("Check-In");

        this.buttonQrCode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(CheckInActivity.this);
                integrator.addExtra("SAVE_HISTORY", false);
                integrator.initiateScan();
            }

        });

        socialAuthAdapter = new SocialAuthAdapter(new ResponseListenerSocialAuth());
        socialAuthAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
        socialAuthAdapter.enable(((Button) findViewById(R.id.activity_checkin_share)));

    }

    private void loadViews() {
        this.progressBar = (ProgressBar) findViewById(R.id.activity_checkin_progres_bar);
        this.textViewMsg = (TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg);
        this.buttonQrCode = (Button) findViewById(R.id.activity_checkin_text_view_qr_code);
        this.buttonShared = (Button) findViewById(R.id.activity_checkin_share);
        this.textViewNumero = (TextView) findViewById(R.id.activity_checkin_text_view_mesa);
        this.actionBar = (ActionBar) findViewById(R.id.actionbar);
        this.imageViewActionBar = (ImageView) this.actionBar.findViewById(R.id.imagem_action_bar);
        this.textViewActionBar = (TextView) this.actionBar.findViewById(R.id.text_view_action_bar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (result != null) {

            String contents = result.getContents();

            if (contents != null) {

                try {

                    String mesaSelecionada = contents.split("&")[0].split("=")[1];
                    String keyMobile = contents.split("&")[1].split("=")[1];

                    if (Utilitarios.isDigit(mesaSelecionada) && Constantes.KEYMOBILE.equals(keyMobile)) {

                        this.conta = this.getDataManager().getContaDAO().get();

                        if (this.conta == null) {

                            String horaAtual = Utilitarios.getHourNow();
                            this.conta = new Conta(Long.valueOf(horaAtual));
                            this.conta.setDataAbertura(horaAtual);
                            this.conta.setNumero(Long.parseLong(mesaSelecionada));
                            this.conta.setTipoConta(Constantes.TipoConta.MESA);

                            Cliente cliente = this.getDataManager().getClienteDAO().get();
                            this.conta.setClienteId(cliente != null ? cliente.getId() : null);

                            changeStatusView(false, false, false, true);

                            this.textViewNumero.setText(this.conta.getNumero().toString());

                        }

                        new ContaRequest(listenerGetConta).post(this.conta);
                        changeStatusView(true, true, false, false);
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

    private void changeStatusView(Boolean isVisibilityProgressBar, Boolean isVisibilityTextView, Boolean isVisibilityButtonQrCode, Boolean isVisibilityButtonShare) {

        this.progressBar.setVisibility(isVisibilityProgressBar ? ProgressBar.VISIBLE : ProgressBar.GONE);

        this.textViewMsg.setVisibility(isVisibilityTextView ? TextView.VISIBLE : TextView.GONE);

        this.buttonQrCode.setVisibility(isVisibilityButtonQrCode ? Button.VISIBLE : Button.GONE);

        this.buttonShared.setVisibility(isVisibilityButtonShare ? Button.VISIBLE : Button.GONE);

    }

    private final class ResponseListenerSocialAuth implements DialogListener {
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
            if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204) {

                Toast.makeText(CheckInActivity.this, "Compartilhado!", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(CheckInActivity.this, "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();
            }

        }
    }
}
