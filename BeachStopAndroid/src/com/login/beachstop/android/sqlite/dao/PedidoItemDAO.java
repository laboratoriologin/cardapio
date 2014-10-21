package com.login.beachstop.android.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.beachstop.android.model.PedidoItem;

public class PedidoItemDAO extends DroidDao<PedidoItem, Long> {

	public PedidoItemDAO(TableDefinition<PedidoItem> tableDefinition, SQLiteDatabase database) {
		super(PedidoItem.class, tableDefinition, database);
	}
}
