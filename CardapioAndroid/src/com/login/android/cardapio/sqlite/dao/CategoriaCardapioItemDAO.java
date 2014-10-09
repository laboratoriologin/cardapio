package com.login.android.cardapio.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.cardapio.model.CategoriaCardapioItem;

public class CategoriaCardapioItemDAO extends DroidDao<CategoriaCardapioItem, Long> {

	public CategoriaCardapioItemDAO(TableDefinition<CategoriaCardapioItem> tableDefinition,
			SQLiteDatabase database) {
		super(CategoriaCardapioItem.class, tableDefinition, database);
	}
}