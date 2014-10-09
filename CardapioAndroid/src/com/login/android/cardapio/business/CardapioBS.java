package com.login.android.cardapio.business;

import com.login.android.cardapio.util.JSONCardapioUtil;

public class CardapioBS implements Observable {

	private BusinessResult businessResult;

	public CardapioBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getCardapioEmpresa() {
		new JSONCardapioUtil(this).execute("");
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
