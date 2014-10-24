package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.model.Cliente;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class CategoriaDAO extends DroidDao<Cliente, Long> {

    public CategoriaDAO(TableDefinition<Categoria> tableDefinition, DataManager dataManager) {
        super(Cliente.class, tableDefinition, dataManager.getDatabase());
    }

    public Cliente get() {

        List<Cliente> listCliente = this.getAll();

        if (listCliente.size() == 0) {
            return null;
        } else {
            return listCliente.get(0);
        }
    }
}
