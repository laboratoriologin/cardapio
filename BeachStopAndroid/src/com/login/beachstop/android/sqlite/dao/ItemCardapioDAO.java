package com.login.beachstop.android.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.beachstop.android.model.ItemCardapio;

public class ItemCardapioDAO extends DroidDao<ItemCardapio, Long> {

	public ItemCardapioDAO(TableDefinition<ItemCardapio> tableDefinition,
			SQLiteDatabase database) {
		super(ItemCardapio.class, tableDefinition, database);
	}
}
