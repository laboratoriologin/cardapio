package com.login.beachstop.android.sqlite;

import org.droidpersistence.dao.TableDefinition;

import com.login.beachstop.android.model.PedidoItem;

public class PedidoItemTableDefinition extends TableDefinition<PedidoItem> {

	public PedidoItemTableDefinition() {
		super(PedidoItem.class);
	}
}