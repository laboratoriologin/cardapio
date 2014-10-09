package com.login.android.cardapio.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.cardapio.model.Pedido;

public class PedidoDAO extends DroidDao<Pedido, Long> {

	public PedidoDAO(TableDefinition<Pedido> tableDefinition, SQLiteDatabase database) {
		super(Pedido.class, tableDefinition, database);
	}
}
