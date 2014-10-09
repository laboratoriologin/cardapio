package com.login.beachstop.android.garcom.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.login.beachstop.android.garcom.R;
import com.login.beachstop.android.garcom.MesaActivity;

public class MesaGridViewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private MesaActivity mesaActivity;
	private List<Long> listMesa;

	public MesaGridViewAdapter(MesaActivity mesaActivity) {
		this.mesaActivity = mesaActivity;
		this.mInflater = LayoutInflater.from(this.mesaActivity.getBaseContext());

		this.listMesa = new ArrayList<Long>();
		for (int i = 1; i <= this.mesaActivity.getQtdMesa(); i++) {
			this.listMesa.add((long) i);
		}
	}

	@Override
	public Long getItem(int position) {
		return this.listMesa.get(position);
	}

	@Override
	public int getCount() {
		return this.listMesa.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Long mesa = this.listMesa.get(position);

		convertView = mInflater.inflate(R.layout.adapter_grid_view_activity_mesa, null);
		TextView tvMesa = (TextView) convertView.findViewById(R.id.adapter_grid_view_activity_mesa_text_view_mesa);

		tvMesa.setText((String.format("%02d", mesa)));

		if (this.mesaActivity.getFiltroMesa().contains(mesa)) {
			tvMesa.setTag(R.id.is_ocupado, true);
			tvMesa.setBackgroundColor(this.mesaActivity.getResources().getColor(R.color.mesa_selecionada));
			tvMesa.setTextColor(this.mesaActivity.getResources().getColor(R.color.branco));

		} else {
			((TextView) convertView.findViewById(R.id.adapter_grid_view_activity_mesa_text_view_mesa)).setTag(R.id.is_ocupado, false);
			tvMesa.setBackgroundColor(this.mesaActivity.getResources().getColor(R.color.mesa_n_selecionada));
			tvMesa.setTextColor(this.mesaActivity.getResources().getColor(R.color.texto_mesa_n_selecionada));
		}

		tvMesa.setTag(R.id.n_mesa, mesa);

		return convertView;
	}
}
