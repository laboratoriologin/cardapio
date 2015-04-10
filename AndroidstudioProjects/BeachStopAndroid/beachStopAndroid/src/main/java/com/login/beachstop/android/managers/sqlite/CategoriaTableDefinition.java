package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.Categoria;

import org.droidpersistence.dao.TableDefinition;

public class CategoriaTableDefinition extends TableDefinition<Categoria> {

    public CategoriaTableDefinition() {
        super(Categoria.class);
    }
}