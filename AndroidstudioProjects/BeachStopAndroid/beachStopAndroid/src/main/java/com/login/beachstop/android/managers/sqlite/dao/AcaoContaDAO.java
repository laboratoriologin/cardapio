package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.AcaoConta;
import com.login.beachstop.android.models.Conta;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class AcaoContaDAO extends DroidDao<AcaoConta, Long> {

    protected DataManager dataManager;

    public AcaoContaDAO(TableDefinition<AcaoConta> tableDefinition, DataManager dataManager) {
        super(AcaoConta.class, tableDefinition, dataManager.getDatabase());
        this.dataManager = dataManager;
    }

    @Override
    public long save(AcaoConta object) throws Exception {
        AcaoConta acaoConta = get();
        if(deleteAll()){
            return super.save(object);
        } else {
            return 0;
        }
    }

    public AcaoConta get() {
        List<AcaoConta> list = this.getAll();
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
