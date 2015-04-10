package com.login.beachstop.android.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.beachstop.android.model.Conta;

public class ContaDAO extends DroidDao<Conta, Long> {

	public ContaDAO(TableDefinition<Conta> tableDefinition, SQLiteDatabase database) {
		super(Conta.class, tableDefinition, database);
	}
}
