package com.login.android.cardapio.garcom.business;

import java.util.List;

import com.login.android.cardapio.garcom.util.JSONAlertasUtil;


public class AlertasBS implements Observable {

	private BusinessResult businessResult;

	public AlertasBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}
	
	@SuppressWarnings("unchecked")
	public void getAlertas(List<Long> mesas) {
		new JSONAlertasUtil(this).execute(mesas);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
