package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.Cliente;

import org.droidpersistence.dao.TableDefinition;

public class ClienteTableDefinition extends TableDefinition<Cliente> {

    public ClienteTableDefinition() {
        super(Cliente.class);
    }
}