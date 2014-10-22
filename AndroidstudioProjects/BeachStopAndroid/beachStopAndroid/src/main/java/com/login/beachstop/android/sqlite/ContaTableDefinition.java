package com.login.beachstop.android.sqlite;

import org.droidpersistence.dao.TableDefinition;

import com.login.beachstop.android.model.Conta;

public class ContaTableDefinition extends TableDefinition<Conta> {

	public ContaTableDefinition() {
		super(Conta.class);
	}
}
