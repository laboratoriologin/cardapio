package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.managers.sqlite.exception.PersistException;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.SubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class ItemDAO extends DroidDao<Item, Long> {

    DataManager dataManager;

    public ItemDAO(TableDefinition<Item> tableDefinition, DataManager dataManager) {
        super(Item.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public List<Item> getAll(Long categoriaId) {

        List<Item> itens = this.getAllbyClause("CATEGORIA_ID=?", new String[]{categoriaId.toString()}, null, null, null);

        List<SubItem> subItens;

        for (Item item : itens) {

            subItens = this.dataManager.getSubItemDAO().getAllbyClause("ITEM_ID=?", new String[]{item.getId().toString()}, null, null, null);
            item.setSubItens(subItens);

        }

        return itens;
    }

    public int getQtdItem() {

        List<Item> item = this.getAll();

        return item.size();

    }

    public void save(List<Item> items) throws Exception {

        try {

            this.dataManager.getDatabase().beginTransaction();

            for (Item item : items) {
                this.save(item);

                for (SubItem subItem : item.getSubItens()) {
                    this.dataManager.getSubItemDAO().save(subItem);
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
