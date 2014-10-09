package com.login.android.cardapio.business;

import com.login.android.cardapio.util.JSONPublicidadeUtil;

public class PublicidadeBS implements Observable {

	private BusinessResult businessResult;

	public PublicidadeBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getPublicidade() {
		new JSONPublicidadeUtil(this).execute();
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
