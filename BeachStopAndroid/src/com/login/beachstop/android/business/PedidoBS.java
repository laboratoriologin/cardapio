package com.login.beachstop.android.business;

import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.util.JSONPedidoUtil;

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
