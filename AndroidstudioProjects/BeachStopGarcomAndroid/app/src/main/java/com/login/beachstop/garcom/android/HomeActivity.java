package com.login.beachstop.garcom.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.AcaoContaRequest;
import com.login.beachstop.garcom.android.network.PausaRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.views.adapters.AcaoContaListViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Argus on 18/12/2014.
 */
public class HomeActivity extends DefaultActivity {

    private final int REFRESH_MILIS = 10000;
    public OnClickListener clickListenerEditarPedido = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), PedidoActivity.class);
            intent.putExtra(Constantes.PARAMETRO_PEDIDO_EDITAR, (AcaoConta) v.getTag());
            HomeActivity.this.startActivity(intent);
        }
    };
    public View.OnClickListener clickListenerAprovarPedido = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //TODO: Incluir tratamento para concorrência.
            Toast.makeText(getApplicationContext(), "Aprovando o pedido...", Toast.LENGTH_SHORT).show();
            ((AcaoConta) view.getTag()).setUsuario(getUsuario());
            new AcaoContaRequest(responseListenerAprovarPedido).aprovarPedido(((AcaoConta) view.getTag()));
            view.setPressed(true);
        }
    };
    public View.OnClickListener clickListenerCancelarPedido = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //TODO: Incluir tratamento para concorrência.
            Toast.makeText(getApplicationContext(), "Cancelando o pedido...", Toast.LENGTH_SHORT).show();
            ((AcaoConta) view.getTag()).setUsuario(getUsuario());
            new AcaoContaRequest(responseListenerCancelarPedido).cancelarPedido(((AcaoConta) view.getTag()));
            view.setPressed(true);
        }
    };
    public View.OnClickListener clickListenerAprovarChamado = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //TODO: Incluir tratamento para concorrência.
            Toast.makeText(getApplicationContext(), "Aprovando chamado...", Toast.LENGTH_SHORT).show();
            ((AcaoConta) view.getTag()).setUsuario(getUsuario());
            new AcaoContaRequest(responseListenerResolverAcaoConta).revolverAcaoConta(((AcaoConta) view.getTag()));
            view.setPressed(true);
        }
    };
    private ListView listView;
    private List<AcaoConta> acaoContas;
    private AcaoContaListViewAdapter acaoContaListViewAdapter;
    public ResponseListener responseListenerAprovarPedido = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    AcaoConta acaoConta = (AcaoConta) serverResponse.getReturnObject();

                    if (acaoContas.contains(acaoConta))
                        acaoContas.remove(acaoContas.indexOf(acaoConta));

                    acaoContaListViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }
        }
    };
    public ResponseListener responseListenerCancelarPedido = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    AcaoConta acaoConta = (AcaoConta) serverResponse.getReturnObject();

                    if (acaoContas.contains(acaoConta))
                        acaoContas.remove(acaoContas.indexOf(acaoConta));

                    acaoContaListViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }
        }
    };
    public ResponseListener responseListenerResolverAcaoConta = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    AcaoConta acaoConta = (AcaoConta) serverResponse.getReturnObject();

                    if (acaoContas.contains(acaoConta))
                        acaoContas.remove(acaoContas.indexOf(acaoConta));

                    acaoContaListViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }
        }
    };
    private String lastHorario = "";
    private ResponseListener responseListenerGetAcaoConta = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    List<AcaoConta> itens = (List<AcaoConta>) serverResponse.getReturnObject();
                    Collections.sort(itens);
                    if (itens.size() > 0) {
                        AcaoConta acaoConta = itens.get(0);
                        if (acaoConta.getHorario().compareTo(lastHorario) > 0) {
                            vibrar();
                            lastHorario = acaoConta.getHorario();
                        }
                    }
                    acaoContas = itens;
                    acaoContaListViewAdapter = new AcaoContaListViewAdapter(HomeActivity.this, acaoContas);
                    listView.setAdapter(acaoContaListViewAdapter);
                    acaoContaListViewAdapter.notifyDataSetChanged();
                } else {
                    if (serverResponse.getStatusCode() != -1)
                        Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh(null);
                }
            }, REFRESH_MILIS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new PausaRequest(new ResponseListener() {
            @Override
            public void onResult(ServerResponse serverResponse) {
                if (serverResponse != null) {
                    if (serverResponse.isOK()) {
                        Intent intent = new Intent(HomeActivity.this, FecharPausaActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else if (ServerResponse.SC_NO_CONTENT == serverResponse.getStatusCode()) {
                        startHome();
                    } else {
                        if (serverResponse.getStatusCode() != -1)
                            Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
                }
            }
        }).getPausa(getUsuario());
    }

    private void startHome() {
        findViewById(R.id.actionbar).findViewById(R.id.imagem_action_bar_menu).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });

        findViewById(R.id.actionbar).findViewById(R.id.imagem_action_bar_novo_pedido).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PedidoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.actionbar).findViewById(R.id.imagem_action_bar_mesa).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SetorActivity.class);
                startActivity(intent);
            }
        });

        this.listView = (ListView) this.findViewById(R.id.home_list_view);
        this.listView.setItemsCanFocus(false);

        acaoContas = new ArrayList<AcaoConta>();
        acaoContaListViewAdapter = new AcaoContaListViewAdapter(this, acaoContas);
        listView.setAdapter(acaoContaListViewAdapter);
        this.refresh(null);
    }

    public void refresh(View view) {
        if (getUsuario() != null) {
            new AcaoContaRequest(responseListenerGetAcaoConta).getAll(getUsuario());
        }
    }

    private void vibrar() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(1500);
    }
}