package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.models.Kit;
import com.login.beachstop.garcom.android.models.KitSubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class KitSubItemDAO extends DroidDao<KitSubItem, Long> {

    DataManager dataManager;

    public KitSubItemDAO(TableDefinition<KitSubItem> tableDefinition, DataManager dataManager) {
        super(KitSubItem.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public List<KitSubItem> getAll(Kit kit){
        return this.dataManager.getKitSubItemDAO().getAllbyClause("KIT_ID=?", new String[]{kit.getId().toString()}, null, null, "");
    }

    public int getQtd() {
        return this.getAll().size();
    }

    public void save(List<KitSubItem> kitSubItens) throws Exception {
        for (KitSubItem item : kitSubItens) {
            this.save(item);
        }
    }
}
