package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.PedidoSubItem;

import org.droidpersistence.dao.TableDefinition;

public class PedidoSubItemTableDefinition extends TableDefinition<PedidoSubItem> {

    public PedidoSubItemTableDefinition() {
        super(PedidoSubItem.class);
    }
}