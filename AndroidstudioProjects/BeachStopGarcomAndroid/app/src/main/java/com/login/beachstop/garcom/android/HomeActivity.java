package com.login.beachstop.garcom.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.views.actionbar.ActionBar;
import com.login.beachstop.garcom.android.views.adapters.AcaoContaListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 18/12/2014.
 */
public class HomeActivity  extends DefaultActivity {

    private ListView listView;
    private List<AcaoConta> pedidosAlertas;
    private AcaoContaListViewAdapter acaoContaListViewAdapter;
    private String lastHorario = "";
    private final int REFRESH_MILIS = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((ActionBar) findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar_novo_pedido).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(HomeActivity.this, PedidoActivity.class);
                //startActivity(intent);
            }
        });

        ((ActionBar) findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar_mesa).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SetorActivity.class);
                startActivity(intent);
            }
        });

        this.listView = (ListView) this.findViewById(R.id.home_list_view);

        this.listView.setItemsCanFocus(false);

        pedidosAlertas = new ArrayList<AcaoConta>();

//        this.adapter = new PedidoAlertaItemAdapter(this, pedidosAlertas);
//
//        this.listView.setAdapter(adapter);

        this.refresh(null);

    }

//    public void getBusinessResult(Object result) {
//        ServerResponse response = (ServerResponse) result;
//        if (response != null) {
//            List<PedidoAlertaItem> itens = (List<PedidoAlertaItem>) response.getReturnObject();
//            Collections.sort(itens);
//            if (itens.size() > 0) {
//                PedidoAlertaItem pedidoAlertaItem = itens.get(0);
//                if (pedidoAlertaItem.getHorario().compareTo(this.lastHorario) > 0) {
//                    this.vibrar();
//                    this.lastHorario = pedidoAlertaItem.getHorario();
//                }
//            }
//
//            this.adapter = new PedidoAlertaItemAdapter(this, itens);
//            this.listView.setAdapter(this.adapter);
//            this.adapter.notifyDataSetChanged();
//        } else {
//            Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
//        }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refresh(null);
//            }
//        }, REFRESH_MILIS);
//    }

    public void refresh(View view) {
        if (getUsuario() != null) {
//            new AlertasBS(this).getAlertas(getUsuario().getMesas());
        }
    }

    private void vibrar() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(1500);
    }
}
