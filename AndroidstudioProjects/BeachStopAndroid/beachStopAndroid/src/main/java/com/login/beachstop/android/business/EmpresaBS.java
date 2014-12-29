package com.login.beachstop.android.business;

import com.login.beachstop.android.util.JSONEmpresaUtil;

public class EmpresaBS implements Observable {

	private BusinessResult businessResult;

	public EmpresaBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getEmpresa() {
		new JSONEmpresaUtil(this).execute("");
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
