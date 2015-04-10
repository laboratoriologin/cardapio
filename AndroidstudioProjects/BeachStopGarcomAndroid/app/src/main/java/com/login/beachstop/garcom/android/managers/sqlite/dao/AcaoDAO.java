package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.models.Acao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class AcaoDAO extends DroidDao<Acao, Long> {

    protected DataManager dataManager;

    public AcaoDAO(TableDefinition<Acao> tableDefinition, DataManager dataManager) {
        super(Acao.class, tableDefinition, dataManager.getDatabase());
        this.dataManager = dataManager;
    }

    public int qtdAll(){
        return getAll().size();
    }

    public void save(List<Acao> acoes) throws Exception {
        for (Acao acao : acoes) {
            this.save(acao);
        }
    }
}
