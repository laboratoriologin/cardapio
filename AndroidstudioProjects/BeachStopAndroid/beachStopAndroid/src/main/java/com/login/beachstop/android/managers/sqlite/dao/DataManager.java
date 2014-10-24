package com.login.beachstop.android.managers.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.beachstop.android.managers.sqlite.ClienteTableDefinition;
import com.login.beachstop.android.managers.sqlite.OpenHelper;

public class DataManager {

    private SQLiteDatabase database;
    private ClienteDAO clienteDAO;

    public DataManager(Context context) {

        SQLiteOpenHelper openHelper = new OpenHelper(context, "cardapio", null, 1);
        this.setDatabase(openHelper.getWritableDatabase());
        this.setClienteDAO(new ClienteDAO(new ClienteTableDefinition(), this));
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
}
