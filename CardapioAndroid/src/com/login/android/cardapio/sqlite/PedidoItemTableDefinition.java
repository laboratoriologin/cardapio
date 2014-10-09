package com.login.android.cardapio.sqlite;

import org.droidpersistence.dao.TableDefinition;
import com.login.android.cardapio.model.PedidoItem;

public class PedidoItemTableDefinition extends TableDefinition<PedidoItem> {

	public PedidoItemTableDefinition() {
		super(PedidoItem.class);
	}
}