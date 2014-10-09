package com.login.android.cardapio.garcom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.login.android.cardapio.garcom.adapter.PedidoAlertaItemAdapter;
import com.login.android.cardapio.garcom.business.AlertasBS;
import com.login.android.cardapio.garcom.business.BusinessResult;
import com.login.android.cardapio.garcom.model.PedidoAlertaItem;
import com.login.android.cardapio.garcom.model.ServerResponse;
import com.login.android.cardapio.garcom.util.Constantes;
import com.login.android.cardapio.garcom.view.ActionBar;

public class HomeActivity extends DefaultActivity implements BusinessResult {

	private ListView listView;
	private List<PedidoAlertaItem> pedidosAlertas;
	private PedidoAlertaItemAdapter adapter;
	private String lastHorario = "";
	private final int REFRESH_MILIS = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		((ActionBar) findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar_novo_pedido).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, PedidoActivity.class);
				startActivity(intent);
			}
		});

		((ActionBar) findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar_mesa).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, MesaActivity.class);
				startActivity(intent);
			}
		});

		this.listView = (ListView) this.findViewById(R.id.home_list_view);

		this.listView.setItemsCanFocus(false);

		pedidosAlertas = new ArrayList<PedidoAlertaItem>();

		this.adapter = new PedidoAlertaItemAdapter(this, pedidosAlertas);

		this.listView.setAdapter(adapter);

		this.refresh(null);

	}

	public void refresh(View view) {

		if (getUsuario() != null) {

			new AlertasBS(this).getAlertas(getUsuario().getMesas());

		}

	}

	@Override
	public void getBusinessResult(Object result) {

		ServerResponse response = (ServerResponse) result;

		if (response != null) {

			List<PedidoAlertaItem> itens = (List<PedidoAlertaItem>) response.getReturnObject();

			Collections.sort(itens);

			if (itens.size() > 0) {

				PedidoAlertaItem pedidoAlertaItem = itens.get(0);

				if (pedidoAlertaItem.getHorario().compareTo(this.lastHorario) > 0) {

					this.vibrar();

					this.lastHorario = pedidoAlertaItem.getHorario();

				}

			}

			this.adapter = new PedidoAlertaItemAdapter(this, itens);

			this.listView.setAdapter(this.adapter);

			this.adapter.notifyDataSetChanged();

		} else {

			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

		}

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				refresh(null);

			}
		}, REFRESH_MILIS);

	}

	private void vibrar() {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		vibrator.vibrate(1500);

	}
}