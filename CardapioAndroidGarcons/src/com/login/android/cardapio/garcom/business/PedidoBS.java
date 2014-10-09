package com.login.android.cardapio.garcom.business;

import com.login.android.cardapio.garcom.model.Pedido;
import com.login.android.cardapio.garcom.model.Usuario;
import com.login.android.cardapio.garcom.util.JSONPedidoUtil;

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
