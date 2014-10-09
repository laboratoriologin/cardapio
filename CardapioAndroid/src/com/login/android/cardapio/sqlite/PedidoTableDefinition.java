package com.login.android.cardapio.sqlite;

import org.droidpersistence.dao.TableDefinition;
import com.login.android.cardapio.model.Pedido;

public class PedidoTableDefinition extends TableDefinition<Pedido> {

	public PedidoTableDefinition() {
		super(Pedido.class);
	}
}