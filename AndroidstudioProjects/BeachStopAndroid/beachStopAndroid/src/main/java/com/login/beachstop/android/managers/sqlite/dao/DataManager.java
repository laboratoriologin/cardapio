package com.login.beachstop.android.managers.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.beachstop.android.managers.sqlite.CategoriaTableDefinition;
import com.login.beachstop.android.managers.sqlite.ClienteTableDefinition;
import com.login.beachstop.android.managers.sqlite.ContaTableDefinition;
import com.login.beachstop.android.managers.sqlite.ItemTableDefinition;
import com.login.beachstop.android.managers.sqlite.KitTableDefinition;
import com.login.beachstop.android.managers.sqlite.OpenHelper;
import com.login.beachstop.android.managers.sqlite.SubItemTableDefinition;

public class DataManager {

    private SQLiteDatabase database;
    private ClienteDAO clienteDAO;
    private CategoriaDAO categoriaDAO;
    private KitDAO kitDAO;
    private ContaDAO contaDAO;
    private ItemDAO itemDAO;
    private SubItemDAO subItemDAO;

    public DataManager(Context context) {

        SQLiteOpenHelper openHelper = new OpenHelper(context, "cardapio", null, 1);
        this.setDatabase(openHelper.getWritableDatabase());
        this.setClienteDAO(new ClienteDAO(new ClienteTableDefinition(), this));
        this.setCategoriaDAO(new CategoriaDAO(new CategoriaTableDefinition(), this));
        this.setKitDAO(new KitDAO(new KitTableDefinition(), this));
        this.setContaDAO(new ContaDAO(new ContaTableDefinition(), this));
        this.setItemDAO(new ItemDAO(new ItemTableDefinition(), this));
        this.setSubItemDAO(new SubItemDAO(new SubItemTableDefinition(), this));

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
}
