package com.login.beachstop.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	TabHost tabHost;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = getTabHost();
		setTabs();
		tabHost.setCurrentTab(0);

		((CardapioApp) getApplication()).setTabHost(tabHost);
	}

	private void setTabs() {
		addTab("Cardápio", R.drawable.tab_cardapio, HomeActivity.class);
		addTab("Sobre o Restaurante", R.drawable.tab_sobre_restaurante, SobreRestauranteActivity.class);
		addTab("Check in", R.drawable.tab_check_in, CheckInActivity.class);
		// addTab("Meus pedidos", R.drawable.tab_pedido, PedidoActivity.class);
		// addTab("Garçom", R.drawable.bt_chamar_garcom,
		// CallGarcomActivity.class);
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}