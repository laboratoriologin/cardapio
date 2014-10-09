package com.login.android.cardapio.sqlite;

import org.droidpersistence.dao.TableDefinition;

import com.login.android.cardapio.model.Empresa;

public class ChaveCardapioEmpresaTableDefinition extends TableDefinition<Empresa> {

	public ChaveCardapioEmpresaTableDefinition() {
		super(Empresa.class);
	}
}
