package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.PedidoSubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

public class PedidoSubItemDAO extends DroidDao<PedidoSubItem, Long> {

    protected DataManager dataManager;

    public PedidoSubItemDAO(TableDefinition<PedidoSubItem> tableDefinition, DataManager dataManager) {
        super(PedidoSubItem.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

}
