package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.models.Setor;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class SetorDAO extends DroidDao<Setor, Long> {

    protected DataManager dataManager;

    public SetorDAO(TableDefinition<Setor> tableDefinition, DataManager dataManager) {
        super(Setor.class, tableDefinition, dataManager.getDatabase());
        this.dataManager = dataManager;
    }

    public int getQtdAll(){
        return this.getAll().size();
    }

    public void save(List<Setor> setores) throws Exception {
        for (Setor setor : setores) {
            this.save(setor);
        }
    }
}
