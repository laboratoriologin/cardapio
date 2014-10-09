package com.login.android.cardapio.garcom.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.cardapio.garcom.model.ItemCardapioSubItem;

public class ItemCardapioSubItemDAO extends DroidDao<ItemCardapioSubItem, Long> {

	public ItemCardapioSubItemDAO(TableDefinition<ItemCardapioSubItem> tableDefinition,
			SQLiteDatabase database) {
		super(ItemCardapioSubItem.class, tableDefinition, database);
	}
}
