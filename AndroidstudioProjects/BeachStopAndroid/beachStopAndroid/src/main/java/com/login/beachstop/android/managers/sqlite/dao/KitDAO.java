package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.managers.sqlite.exception.PersistException;
import com.login.beachstop.android.models.Kit;
import com.login.beachstop.android.models.KitSubItem;
import com.login.beachstop.android.models.SubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class KitDAO extends DroidDao<Kit, Long> {

    DataManager dataManager;

    public KitDAO(TableDefinition<Kit> tableDefinition, DataManager dataManager) {
        super(Kit.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public Kit get(Long id) {
        Kit kit = super.get(id);
        kit.setKitSubItens(this.dataManager.getKitSubItemDAO().getAll(kit));

        SubItem subItem;

        for (KitSubItem kitSubItem : kit.getKitSubItens()) {
            subItem = new SubItem();
            subItem.setId(kitSubItem.getSubItemId());
            kitSubItem.setItem(this.dataManager.getItemDAO().getBySubItem(subItem));
        }


        return kit;
    }

    public List<Kit> getAll() {
        List<Kit> kits = super.getAll();
        SubItem subItem;

        for (Kit kit : kits) {
            kit.setKitSubItens(this.dataManager.getKitSubItemDAO().getAll(kit));

            for (KitSubItem kitSubItem : kit.getKitSubItens()) {
                subItem = new SubItem();
                subItem.setId(kitSubItem.getSubItemId());
                kitSubItem.setItem(this.dataManager.getItemDAO().getBySubItem(subItem));
            }
        }
        return kits;
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
