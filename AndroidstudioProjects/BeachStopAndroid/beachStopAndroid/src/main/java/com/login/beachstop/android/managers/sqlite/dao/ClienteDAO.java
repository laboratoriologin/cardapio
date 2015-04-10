package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.Cliente;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class ClienteDAO extends DroidDao<Cliente, Long> {

    protected DataManager dataManager;

    public ClienteDAO(TableDefinition<Cliente> tableDefinition, DataManager dataManager) {
        super(Cliente.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public Cliente get() {

        List<Cliente> listCliente = this.getAll();

        if (listCliente.size() == 0) {
            return null;
        } else {
            return listCliente.get(0);
        }
    }

    public boolean hasCliente() {

        List<Cliente> listCliente = this.getAll();

        if (listCliente.size() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
