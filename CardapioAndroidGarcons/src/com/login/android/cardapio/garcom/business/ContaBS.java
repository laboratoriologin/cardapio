package com.login.android.cardapio.garcom.business;

import com.login.android.cardapio.garcom.util.JSONHistoricoContaUtil;

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
