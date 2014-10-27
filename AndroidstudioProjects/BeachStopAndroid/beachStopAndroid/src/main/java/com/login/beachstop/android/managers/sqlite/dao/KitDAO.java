package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.Kit;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class KitDAO extends DroidDao<Kit, Long> {

    DataManager dataManager;

    public KitDAO(TableDefinition<Kit> tableDefinition, DataManager dataManager) {
        super(Kit.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public int getQtdCategoria() {

        List<Kit> kit = this.getAll();

        return kit.size();

    }

    public void save(List<Kit> kits) throws Exception {

        for (Kit kit : kits) {
            this.save(kit);
        }

    }
}
