package com.login.beachstop.android.garcom.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.beachstop.android.garcom.model.CategoriaCardapioItem;

public class CategoriaCardapioItemDAO extends DroidDao<CategoriaCardapioItem, Long> {

	public CategoriaCardapioItemDAO(TableDefinition<CategoriaCardapioItem> tableDefinition,
			SQLiteDatabase database) {
		super(CategoriaCardapioItem.class, tableDefinition, database);
	}
}
