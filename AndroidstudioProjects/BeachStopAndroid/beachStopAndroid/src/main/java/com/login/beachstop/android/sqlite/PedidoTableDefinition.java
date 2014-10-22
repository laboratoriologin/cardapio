package com.login.beachstop.android.sqlite;

import org.droidpersistence.dao.TableDefinition;

import com.login.beachstop.android.model.Pedido;

public class PedidoTableDefinition extends TableDefinition<Pedido> {

	public PedidoTableDefinition() {
		super(Pedido.class);
	}
}