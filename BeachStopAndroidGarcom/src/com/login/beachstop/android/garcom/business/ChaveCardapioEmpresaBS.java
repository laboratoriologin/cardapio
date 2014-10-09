package com.login.beachstop.android.garcom.business;

import com.login.beachstop.android.garcom.util.JSONChaveCardapioEmpresaUtil;

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
