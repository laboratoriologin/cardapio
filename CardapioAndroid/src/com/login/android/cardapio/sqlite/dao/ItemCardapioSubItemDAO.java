package com.login.android.cardapio.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.cardapio.model.ItemCardapioSubItem;

public class ItemCardapioSubItemDAO extends DroidDao<ItemCardapioSubItem, Long> {

	public ItemCardapioSubItemDAO(TableDefinition<ItemCardapioSubItem> tableDefinition,
			SQLiteDatabase database) {
		super(ItemCardapioSubItem.class, tableDefinition, database);
	}
}
