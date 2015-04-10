package com.login.beachstop.garcom.android.managers.sqlite;

import com.login.beachstop.garcom.android.models.Categoria;

import org.droidpersistence.dao.TableDefinition;

public class CategoriaTableDefinition extends TableDefinition<Categoria> {

    public CategoriaTableDefinition() {
        super(Categoria.class);
    }
}