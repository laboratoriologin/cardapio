package com.login.android.cardapio.sqlite;

import org.droidpersistence.dao.TableDefinition;

import com.login.android.cardapio.model.Conta;

public class ContaTableDefinition extends TableDefinition<Conta> {

	public ContaTableDefinition() {
		super(Conta.class);
	}
}
