package com.login.beachstop.android.business;

import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.util.JSONCheckContaAbertaUtil;
import com.login.beachstop.android.util.JSONContaAllUtil;
import com.login.beachstop.android.util.JSONContaUtil;

public class ContaBS implements Observable {

	private BusinessResult businessResult;

	public ContaBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getContaFinal(Conta argConta) {
		new JSONContaAllUtil(this).execute(argConta);
	}

	public void getContaMesa(Conta argConta) {
		new JSONContaUtil(this).execute(argConta);
	}

	public void chkContaAberta(Conta argConta) {
		new JSONCheckContaAbertaUtil(this).execute(argConta);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
