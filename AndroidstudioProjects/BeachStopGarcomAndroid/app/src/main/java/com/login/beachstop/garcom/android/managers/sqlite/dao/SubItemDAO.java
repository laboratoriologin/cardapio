package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.models.Item;
import com.login.beachstop.garcom.android.models.SubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class SubItemDAO extends DroidDao<SubItem, Long> {

    DataManager dataManager;

    public SubItemDAO(TableDefinition<SubItem> tableDefinition, DataManager dataManager) {
        super(SubItem.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public List<SubItem> getByItemId(Item item){
        return this.getAllbyClause("ITEM_ID=?", new String[]{item.getId().toString()}, null, null, "ORDEM");
    }

    public List<SubItem> getAllWithItem() {
        List<SubItem> subItens = getAllbyClause("", null, "", "", "ITEM_ID");
        for (SubItem subItem : subItens) {
            subItem.setItem(this.dataManager.getItemDAO().get(subItem.getItemId()));
        }
        //Collections.sort(subItens);
        return subItens;
    }

    public SubItem getWithItem(Long id) {
        SubItem subItem = this.get(id);
        subItem.setItem(this.dataManager.getItemDAO().get(subItem.getItemId()));
        return subItem;
    }

    public int getQtdSubItem() {

        List<SubItem> subItens = this.getAll();

        return subItens.size();

    }

    public void save(List<SubItem> subItens) throws Exception {

        for (SubItem subItem : subItens) {
            this.save(subItem);
        }

    }
}
