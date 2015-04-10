package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.Conta;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class ContaDAO extends DroidDao<Conta, Long> {

    protected DataManager dataManager;

    public ContaDAO(TableDefinition<Conta> tableDefinition, DataManager dataManager) {
        super(Conta.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public Conta get() {
        List<Conta> contas = this.getAll();
        if (contas.size() == 0) {
            return null;
        } else {
            return contas.get(0);
        }
    }
}
