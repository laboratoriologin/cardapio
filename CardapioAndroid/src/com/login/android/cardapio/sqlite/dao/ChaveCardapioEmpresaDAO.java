package com.login.android.cardapio.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.cardapio.model.Empresa;

public class ChaveCardapioEmpresaDAO extends DroidDao<Empresa, Long> {

	public ChaveCardapioEmpresaDAO(TableDefinition<Empresa> tableDefinition, SQLiteDatabase database) {
		super(Empresa.class, tableDefinition, database);
	}
}
