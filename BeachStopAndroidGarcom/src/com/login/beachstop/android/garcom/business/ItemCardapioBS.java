package com.login.beachstop.android.garcom.business;

import java.util.ArrayList;
import java.util.List;

import com.login.beachstop.android.garcom.util.JSONItemCardapioUtil;

public class ItemCardapioBS implements Observable {

	private BusinessResult businessResult;

	public ItemCardapioBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	@SuppressWarnings("unchecked")
	public void getCardapioEmpresa(Long categoriaCardapio) {

		List<Long> listCategoriaCardapio = new ArrayList<Long>();
		listCategoriaCardapio.add(categoriaCardapio);
		new JSONItemCardapioUtil(this).execute(listCategoriaCardapio);
	}

	@SuppressWarnings("unchecked")
	public void getCardapioEmpresa(List<Long> listCategoriaCardapio) {
		new JSONItemCardapioUtil(this).execute(listCategoriaCardapio);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
	}
}
