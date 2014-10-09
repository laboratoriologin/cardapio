package com.login.android.cardapio.business;

import com.login.android.cardapio.model.Pedido;
import com.login.android.cardapio.util.JSONPedidoUtil;

public class PedidoBS implements Observable {

	private BusinessResult businessResult;

	public PedidoBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void save(Pedido pedido) {
		new JSONPedidoUtil(this).execute(pedido);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
