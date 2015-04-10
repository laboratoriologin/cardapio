package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.Pedido;

import org.droidpersistence.dao.TableDefinition;

public class PedidoTableDefinition extends TableDefinition<Pedido> {

    public PedidoTableDefinition() {
        super(Pedido.class);
    }
}