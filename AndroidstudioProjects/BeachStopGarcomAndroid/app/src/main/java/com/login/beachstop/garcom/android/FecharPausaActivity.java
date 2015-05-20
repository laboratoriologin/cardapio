package com.login.beachstop.garcom.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.garcom.android.models.Pausa;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.PausaRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

/**
 * Created by Argus on 18/12/2014.
 */
public class FecharPausaActivity extends DefaultActivity {

    private final int REFRESH_MILIS = 10000;
    private TextView textViewTempo;
    private Button buttonFecharPausa;
    private ProgressBar progressBar;
    private TextView textViewMsg;
    private Boolean atualizaTempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechar_pausa);

        atualizaTempo = true;

        textViewTempo = (TextView) findViewById(R.id.activity_fechar_pausa_text_view_tempo_pausa);
        progressBar = (ProgressBar) findViewById(R.id.activity_fechar_pausa_progresbar);
        textViewMsg = (TextView) findViewById(R.id.activity_fechar_pausa_textview_msg);

        buttonFecharPausa = (Button) findViewById(R.id.activity_fechar_pausa_button);
        buttonFecharPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(ProgressBar.VISIBLE);
                textViewMsg.setVisibility(TextView.VISIBLE);
                textViewMsg.setText("Finalizando a pausa...");
                buttonFecharPausa.setVisibility(Button.GONE);

                new PausaRequest(new ResponseListener() {
                    @Override
                    public void onResult(ServerResponse serverResponse) {
                        if (serverResponse != null) {
                            if (serverResponse.isOK()) {
                                textViewMsg.setText("Pausa finalizada com sucesso!");

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        atualizaTempo = false;
                                        Intent intent = new Intent(FecharPausaActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, REFRESH_MILIS);

                            } else {
                                textViewMsg.setText(serverResponse.getMsgErro());
                                buttonFecharPausa.setVisibility(Button.VISIBLE);
                            }
                        } else {
                            textViewMsg.setText(Constantes.MSG_ERRO_NET);
                            buttonFecharPausa.setVisibility(Button.VISIBLE);
                        }
                    }
                }).fecharPausa(getUsuario());
            }
        });
        loadPausa();
    }

    private void loadPausa() {
        new PausaRequest(new ResponseListener() {
            @Override
            public void onResult(ServerResponse serverResponse) {
                if (serverResponse != null) {
                    if (serverResponse.isOK()) {
                        textViewTempo.setText(((Pausa) serverResponse.getReturnObject()).getHorarioDiff() + "mim");
                    } else if (ServerResponse.SC_NO_CONTENT == serverResponse.getStatusCode()) {
                        atualizaTempo = false;
                        Intent intent = new Intent(FecharPausaActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

                if (atualizaTempo) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadPausa();
                        }
                    }, REFRESH_MILIS);
                }
            }
        }).getPausa(getUsuario());
    }
}
