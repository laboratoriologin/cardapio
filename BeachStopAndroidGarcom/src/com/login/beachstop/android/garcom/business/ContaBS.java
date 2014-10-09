package com.login.beachstop.android.garcom.business;

import com.login.beachstop.android.garcom.util.JSONHistoricoContaUtil;

public class ContaBS implements Observable {

	private BusinessResult businessResult;

	public ContaBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getHistoricoConta(Long nMesa) {
		new JSONHistoricoContaUtil(this).execute(nMesa);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
