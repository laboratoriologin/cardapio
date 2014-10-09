package com.login.android.cardapio.garcom;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.cardapio.garcom.adapter.MesaGridViewAdapter;
import com.login.android.cardapio.garcom.business.BusinessResult;
import com.login.android.cardapio.garcom.business.UsuarioMesaBS;
import com.login.android.cardapio.garcom.model.ServerResponse;
import com.login.android.cardapio.garcom.model.Usuario;
import com.login.android.cardapio.garcom.util.Constantes;
import com.login.android.cardapio.garcom.view.ActionBar;

public class MesaActivity extends DefaultActivity implements BusinessResult {

	private ProgressDialog progressDialog;
	private MesaGridViewAdapter mesaGridViewAdapter;
	private GridView gridViewMesa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mesa);
		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		((ActionBar) findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar_salvar_mesa).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog = new ProgressDialog(MesaActivity.this);
				progressDialog.setMessage("Salvando...");
				progressDialog.show();
				progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

				try {
					new UsuarioMesaBS(MesaActivity.this).salvar(getUsuario(), getFiltroMesa());
				} catch (Exception e) {
					progressDialog.dismiss();
					Toast.makeText(MesaActivity.this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}
		});

		this.gridViewMesa = (GridView) findViewById(R.id.activity_gridview_mesa);

		progressDialog = new ProgressDialog(MesaActivity.this);
		progressDialog.setMessage("Carregando...");
		progressDialog.show();
		progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

		try {
			new UsuarioMesaBS(this).getAll(getUsuario());
		} catch (Exception e) {
			progressDialog.dismiss();
			Toast.makeText(this, Constantes.MSG_ERRO_GRAVE_SISTEMA, Toast.LENGTH_SHORT).show();
		}

		this.gridViewMesa.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				TextView txv = (TextView) arg1.findViewById(R.id.adapter_grid_view_activity_mesa_text_view_mesa);
				List<Long> listMesaTemp;

				if ((Boolean) txv.getTag(R.id.is_ocupado)) {
					int index = getFiltroMesa().indexOf((Long) txv.getTag(R.id.n_mesa));
					listMesaTemp = getFiltroMesa();
					listMesaTemp.remove(index);
					setFiltroMesa(listMesaTemp);
					txv.setBackgroundColor(MesaActivity.this.getResources().getColor(R.color.mesa_n_selecionada));
					txv.setTag(R.id.is_ocupado, false);

				} else {
					listMesaTemp = getFiltroMesa();
					listMesaTemp.add((Long) txv.getTag(R.id.n_mesa));
					setFiltroMesa(listMesaTemp);
					txv.setBackgroundColor(MesaActivity.this.getResources().getColor(R.color.mesa_selecionada));
					txv.setTag(R.id.is_ocupado, true);
				}

			}
		});

	}

	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;

		progressDialog.dismiss();
		if (serverResponse.isOK()) {

			if (serverResponse.getReturnObject() == null) {
				progressDialog.dismiss();
				Toast.makeText(this, Constantes.MSG_OK, Toast.LENGTH_SHORT).show();
			} else {

				this.setFiltroMesa((List<Long>) serverResponse.getReturnObject());

				this.mesaGridViewAdapter = new MesaGridViewAdapter(this);
				this.gridViewMesa.setAdapter(this.mesaGridViewAdapter);

				progressDialog.dismiss();
			}
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_GRAVE_SISTEMA, Toast.LENGTH_SHORT).show();
		}

	}
}