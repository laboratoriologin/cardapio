package com.login.beachstop.garcom.android.managers.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.beachstop.garcom.android.managers.sqlite.CategoriaTableDefinition;
import com.login.beachstop.garcom.android.managers.sqlite.EmpresaTableDefinition;
import com.login.beachstop.garcom.android.managers.sqlite.ItemTableDefinition;
import com.login.beachstop.garcom.android.managers.sqlite.KitSubItemTableDefinition;
import com.login.beachstop.garcom.android.managers.sqlite.KitTableDefinition;
import com.login.beachstop.garcom.android.managers.sqlite.OpenHelper;
import com.login.beachstop.garcom.android.managers.sqlite.SubItemTableDefinition;

/**
 * Created by Argus on 16/12/2014.
 */
public class DataManager {

    private SQLiteDatabase database;
    private EmpresaDAO empresaDAO;
    private CategoriaDAO categoriaDAO;
    private ItemDAO itemDAO;
    private SubItemDAO subItemDAO;
    private KitDAO kitDAO;
    private KitSubItemDAO kitSubItemDAO;


    public DataManager(Context context) {
        SQLiteOpenHelper openHelper = new OpenHelper(context, "garcom", null, 1);
        this.database = openHelper.getWritableDatabase();
        this.setEmpresaDAO(new EmpresaDAO(new EmpresaTableDefinition(), this));
        this.setCategoriaDAO(new CategoriaDAO(new CategoriaTableDefinition(), this));
        this.setItemDAO(new ItemDAO(new ItemTableDefinition(), this));
        this.setSubItemDAO(new SubItemDAO(new SubItemTableDefinition(), this));
        this.setKitDAO(new KitDAO(new KitTableDefinition(), this));
        this.setKitSubItemDAO(new KitSubItemDAO(new KitSubItemTableDefinition(), this));

    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public EmpresaDAO getEmpresaDAO() {
        return empresaDAO;
    }

    public void setEmpresaDAO(EmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
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

    public KitDAO getKitDAO() {
        return kitDAO;
    }

    public void setKitDAO(KitDAO kitDAO) {
        this.kitDAO = kitDAO;
    }


    public KitSubItemDAO getKitSubItemDAO() {
        return kitSubItemDAO;
    }

    public void setKitSubItemDAO(KitSubItemDAO kitSubItemDAO) {
        this.kitSubItemDAO = kitSubItemDAO;
    }
}
