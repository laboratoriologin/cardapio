package com.login.beachstop.android.business;

import com.login.beachstop.android.util.JSONTokenUtil;

public class TokenBS implements Observable {

	private BusinessResult businessResult;

	public TokenBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void inserir(String regId, String keyMobile) {
		new JSONTokenUtil(this).execute(regId, keyMobile);
	}

	@Override
	public void observe(Object result) throws Exception {

	}
}
