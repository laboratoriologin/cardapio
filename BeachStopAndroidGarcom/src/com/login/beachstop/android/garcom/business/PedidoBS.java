package com.login.beachstop.android.garcom.business;

import com.login.beachstop.android.garcom.model.Pedido;
import com.login.beachstop.android.garcom.model.Usuario;
import com.login.beachstop.android.garcom.util.JSONPedidoUtil;

public class PedidoBS implements Observable {

	private BusinessResult businessResult;

	public PedidoBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void insert(Pedido pedido) {
		new JSONPedidoUtil(this).execute(pedido, true);
	}

	public void update(Pedido pedido) {
		new JSONPedidoUtil(this).execute(pedido, false);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
