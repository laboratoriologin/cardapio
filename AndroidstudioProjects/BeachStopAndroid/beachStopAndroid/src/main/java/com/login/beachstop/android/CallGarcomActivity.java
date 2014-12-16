package com.login.beachstop.android;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.actionbar.ActionBar;

/**
 * Created by Argus on 16/12/2014.
 */
public class CallGarcomActivity extends DefaultActivity {

    private Button btnChamarGarcom;
    private Button btnFecharConta;
    private ResponseListener responseListenerConta = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    Toast.makeText(CallGarcomActivity.this, "Uma Garçom irá lhe atender em breve! Divirta-se!", Toast.LENGTH_SHORT).show();

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
        ((ImageView) (findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar)).setBackgroundResource(R.drawable.bt_menu_chamados);
        ((TextView) (findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Chamar garçom");

        this.btnChamarGarcom = (Button) findViewById(R.id.activity_call_garcom_button_call_garcom);
        this.btnFecharConta = (Button) findViewById(R.id.tab_fragment_conta_button_enviar_conta);

        this.btnChamarGarcom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (getDataManager().getContaDAO().get() != null) {

                    //new AlertaBS(CallGarcomActivity.this).callWaiter(getDataManager().getConta());

                } else {

                    Toast.makeText(CallGarcomActivity.this, "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}