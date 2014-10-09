package com.login.android.cardapio.garcom.business;

import java.util.List;

import com.login.android.cardapio.garcom.model.Usuario;
import com.login.android.cardapio.garcom.util.JSONUsuarioMesa;

public class UsuarioMesaBS implements Observable {

	private BusinessResult businessResult;

	public UsuarioMesaBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void getAll(Usuario usuario) throws Exception {
		new JSONUsuarioMesa(this).execute(usuario, null);
	}

	public void salvar(Usuario usuario, List<Long> nMesa) throws Exception {
		new JSONUsuarioMesa(this).execute(usuario, nMesa);
	}

	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);

	}
}
