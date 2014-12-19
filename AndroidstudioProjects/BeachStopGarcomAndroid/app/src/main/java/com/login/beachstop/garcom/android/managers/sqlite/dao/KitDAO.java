package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.models.Kit;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class KitDAO extends DroidDao<Kit, Long> {

    DataManager dataManager;

    public KitDAO(TableDefinition<Kit> tableDefinition, DataManager dataManager) {
        super(Kit.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public int getQtdKit() {

        List<Kit> kit = this.getAll();

        return kit.size();

    }

    public void save(List<Kit> kits) throws Exception {

        for (Kit kit : kits) {
            this.save(kit);
        }

    }
}
