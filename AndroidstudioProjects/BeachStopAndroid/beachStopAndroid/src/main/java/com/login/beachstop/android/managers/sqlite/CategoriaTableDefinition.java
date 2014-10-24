package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.model.Cliente;

import org.droidpersistence.dao.TableDefinition;

public class CategoriaTableDefinition extends TableDefinition<Cliente> {

    public CategoriaTableDefinition() {
        super(Cliente.class);
    }
}