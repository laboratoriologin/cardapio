package com.login.beachstop.android;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.models.AcaoConta;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.AcaoContaRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.actionbar.ActionBar;

/**
 * Created by Argus on 16/12/2014.
 */
public class CallGarcomActivity extends DefaultActivity {

    private ProgressDialog progressDialog;
    private Button btnChamarGarcom;
    private Button btnFecharConta;
    private ResponseListener responseListenerChamarGarcom = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    progressDialog.dismiss();
                    Toast.makeText(CallGarcomActivity.this, "Uma Garçom irá lhe atender em breve! Divirta-se!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CallGarcomActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CallGarcomActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }
        }

    };

    private ResponseListener responseListenerFecharConta = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    progressDialog.dismiss();
                    Toast.makeText(CallGarcomActivity.this, "Sua conta está sendo processada! Obrigado e volte sempre!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CallGarcomActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CallGarcomActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_garcom);

        ((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);
        (findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar).setBackgroundResource(R.drawable.bt_menu_chamados);
        ((TextView) (findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Chamar garçom");

        this.btnChamarGarcom = (Button) findViewById(R.id.activity_call_garcom_button_call_garcom);
        this.btnFecharConta = (Button) findViewById(R.id.activity_call_garcom_button_fechar_conta);

        this.btnChamarGarcom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (getDataManager().getContaDAO().get() != null) {

                    progressDialog = new ProgressDialog(CallGarcomActivity.this);
                    progressDialog.setMessage("Chamando nossos garçons...");
                    progressDialog.show();
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    AcaoConta acaoConta = new AcaoConta();
                    acaoConta.setAcaoId(Constantes.Acoes.ChamarGarcom);
                    acaoConta.setConta(getDataManager().getContaDAO().get());

                    new AcaoContaRequest(responseListenerChamarGarcom).chamarGgarcom(acaoConta);

                } else {
                    Toast.makeText(CallGarcomActivity.this, "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.btnFecharConta.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (getDataManager().getContaDAO().get() != null) {

                    progressDialog = new ProgressDialog(CallGarcomActivity.this);
                    progressDialog.setMessage("Fechando sua conta...");
                    progressDialog.show();
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    AcaoConta acaoConta = new AcaoConta();
                    acaoConta.setAcaoId(Constantes.Acoes.PedirConta);
                    acaoConta.setConta(getDataManager().getContaDAO().get());

                    new AcaoContaRequest(responseListenerChamarGarcom).fecharConta(acaoConta);

                } else {
                    Toast.makeText(CallGarcomActivity.this, "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}