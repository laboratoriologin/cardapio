package com.login.beachstop.android.managers.sqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.beachstop.android.managers.sqlite.CategoriaTableDefinition;
import com.login.beachstop.android.managers.sqlite.ClienteTableDefinition;
import com.login.beachstop.android.managers.sqlite.ContaTableDefinition;
import com.login.beachstop.android.managers.sqlite.ItemTableDefinition;
import com.login.beachstop.android.managers.sqlite.KitSubItemTableDefinition;
import com.login.beachstop.android.managers.sqlite.KitTableDefinition;
import com.login.beachstop.android.managers.sqlite.OpenHelper;
import com.login.beachstop.android.managers.sqlite.PedidoSubItemTableDefinition;
import com.login.beachstop.android.managers.sqlite.PedidoTableDefinition;
import com.login.beachstop.android.managers.sqlite.SubItemTableDefinition;

import org.droidpersistence.annotation.Table;

public class DataManager {

    private SQLiteDatabase database;
    private ClienteDAO clienteDAO;
    private CategoriaDAO categoriaDAO;
    private KitDAO kitDAO;
    private KitSubItemDAO kitSubItemDAO;
    private ContaDAO contaDAO;
    private ItemDAO itemDAO;
    private SubItemDAO subItemDAO;
    private PedidoDAO pedidoDAO;
    private PedidoSubItemDAO pedidoSubItemDAO;

    public DataManager(Context context) {

        SQLiteOpenHelper openHelper = new OpenHelper(context, "cardapio", null, 1);
        this.setDatabase(openHelper.getWritableDatabase());

        this.setClienteDAO(new ClienteDAO(new ClienteTableDefinition(), this));
        this.setCategoriaDAO(new CategoriaDAO(new CategoriaTableDefinition(), this));
        this.setKitDAO(new KitDAO(new KitTableDefinition(), this));
        this.setKitSubItemDAO(new KitSubItemDAO(new KitSubItemTableDefinition(), this));
        this.setContaDAO(new ContaDAO(new ContaTableDefinition(), this));
        this.setItemDAO(new ItemDAO(new ItemTableDefinition(), this));
        this.setSubItemDAO(new SubItemDAO(new SubItemTableDefinition(), this));
        this.setPedidoDAO(new PedidoDAO(new PedidoTableDefinition(), this));
        this.setPedidoSubItemDAO(new PedidoSubItemDAO(new PedidoSubItemTableDefinition(), this));

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Long getNextId(Class c) {

        Table annotation = (Table) c.getAnnotation(Table.class);

        final Cursor cursor = this.database.rawQuery("SELECT MAX(ID)  FROM " + annotation.name() + ";", null);
        Long id = 0l;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    id = cursor.getLong(0);
                }
            } catch (Exception e) {
                return null;
            } finally {
                cursor.close();
            }
        } else {
            return null;
        }

        id++;

        return id;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public KitDAO getKitDAO() {
        return kitDAO;
    }

    public void setKitDAO(KitDAO kitDAO) {
        this.kitDAO = kitDAO;
    }

    public ContaDAO getContaDAO() {
        return contaDAO;
    }

    public void setContaDAO(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public SubItemDAO getSubItemDAO() {
        return subItemDAO;
    }

    public void setSubItemDAO(SubItemDAO subItemDAO) {
        this.subItemDAO = subItemDAO;
    }

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public PedidoSubItemDAO getPedidoSubItemDAO() {
        return pedidoSubItemDAO;
    }

    public void setPedidoSubItemDAO(PedidoSubItemDAO pedidoSubItemDAO) {
        this.pedidoSubItemDAO = pedidoSubItemDAO;
    }

    public KitSubItemDAO getKitSubItemDAO() {
        return kitSubItemDAO;
    }

    public void setKitSubItemDAO(KitSubItemDAO kitSubItemDAO) {
        this.kitSubItemDAO = kitSubItemDAO;
    }
}
