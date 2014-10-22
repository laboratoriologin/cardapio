package com.login.beachstop.android.business;

import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.util.JSONAlertaUtil;

public class AlertaBS implements Observable {

	private BusinessResult businessResult;

	public AlertaBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void callWaiter(Conta conta) {
		new JSONAlertaUtil(this).execute(conta);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
