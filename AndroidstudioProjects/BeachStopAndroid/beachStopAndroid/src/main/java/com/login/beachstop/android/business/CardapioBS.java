package com.login.beachstop.android.business;

import com.login.beachstop.android.util.JSONCardapioUtil;

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
