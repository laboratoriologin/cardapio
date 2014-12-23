package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.managers.sqlite.exception.PersistException;
import com.login.beachstop.android.models.Kit;
import com.login.beachstop.android.models.KitSubItem;

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

        try {
            this.dataManager.getDatabase().beginTransaction();

            for (Kit kit : kits) {
                this.save(kit);

                for (KitSubItem kitSubItem : kit.getKitSubItens()) {
                    this.dataManager.getKitSubItemDAO().save(kitSubItem);
                }

            }
            this.dataManager.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            throw new PersistException(ex);
        } finally {
            this.dataManager.getDatabase().endTransaction();
        }
    }
}
