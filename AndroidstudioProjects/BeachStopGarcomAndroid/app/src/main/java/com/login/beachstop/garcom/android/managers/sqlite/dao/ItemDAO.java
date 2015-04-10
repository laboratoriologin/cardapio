package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.managers.sqlite.exception.PersistException;
import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.Item;
import com.login.beachstop.garcom.android.models.SubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DroidDao<Item, Long> {

    DataManager dataManager;

    public ItemDAO(TableDefinition<Item> tableDefinition, DataManager dataManager) {
        super(Item.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    @Override
    public List<Item> getAll(){
        List<Item> itens = super.getAll();
        for (Item item : itens){
            item.setSubItens(this.dataManager.getSubItemDAO().getByItemId(item));
        }
        return itens;
    }

    public List<Item> getAll(Long categoriaId) {

        List<Item> itens = this.getAllbyClause("CATEGORIA_ID=?", new String[]{categoriaId.toString()}, null, null, "ORDEM");

        List<SubItem> subItens;

        for (Item item : itens) {

            subItens = this.dataManager.getSubItemDAO().getByItemId(item);
            item.setSubItens(subItens);

        }

        return itens;
    }

    public int getQtdItem(Categoria categoria) {

        List<Item> item;

        if (categoria == null) {

            item = this.getAll();

        } else {

            item = this.getAll(categoria.getId());

        }

        return item.size();
    }

    public Item getBySubItem(SubItem subItem) {

        Item item;
        SubItem subItem1 = this.dataManager.getSubItemDAO().get(subItem.getId());

        if (subItem1 != null) {
            item = get(subItem1.getItemId());
            item.setSubItens(new ArrayList<SubItem>());
            item.getSubItens().add(subItem1);
            return item;
        }
        return null;
    }

    public Item getBySubItem(Long idSubItem) {

        Item item;
        SubItem subItem1 = this.dataManager.getSubItemDAO().get(idSubItem);

        if (subItem1 != null) {
            item = get(subItem1.getItemId());
            item.setSubItens(new ArrayList<SubItem>());
            item.getSubItens().add(subItem1);
            return item;
        }
        return null;
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
