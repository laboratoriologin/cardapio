package com.login.beachstop.android.garcom.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.beachstop.android.garcom.model.ChaveCardapioEmpresa;

public class ChaveCardapioEmpresaDAO extends DroidDao<ChaveCardapioEmpresa, Long> {

	public ChaveCardapioEmpresaDAO(TableDefinition<ChaveCardapioEmpresa> tableDefinition, SQLiteDatabase database) {
		super(ChaveCardapioEmpresa.class, tableDefinition, database);
	}
}
