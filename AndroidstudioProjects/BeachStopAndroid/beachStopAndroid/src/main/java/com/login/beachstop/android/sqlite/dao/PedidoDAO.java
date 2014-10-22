package com.login.beachstop.android.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.beachstop.android.model.Pedido;

public class PedidoDAO extends DroidDao<Pedido, Long> {

	public PedidoDAO(TableDefinition<Pedido> tableDefinition, SQLiteDatabase database) {
		super(Pedido.class, tableDefinition, database);
	}
}
