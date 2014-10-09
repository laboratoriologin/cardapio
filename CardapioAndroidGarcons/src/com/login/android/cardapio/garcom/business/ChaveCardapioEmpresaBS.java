package com.login.android.cardapio.garcom.business;

import com.login.android.cardapio.garcom.util.JSONChaveCardapioEmpresaUtil;

public class ChaveCardapioEmpresaBS implements Observable {

	private BusinessResult businessResult;

	public ChaveCardapioEmpresaBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getChaveCardapioEmpresa() {
		new JSONChaveCardapioEmpresaUtil(this).execute("");
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
