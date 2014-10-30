package com.login.beachstop.android.managers.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    public OpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            new ClienteTableDefinition().onCreate(db);
            new CategoriaTableDefinition().onCreate(db);
            new KitTableDefinition().onCreate(db);
            new ContaTableDefinition().onCreate(db);
            new ItemTableDefinition().onCreate(db);
            new SubItemTableDefinition().onCreate(db);
            new PedidoTableDefinition().onCreate(db);
            new PedidoSubItemTableDefinition().onCreate(db);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            new ClienteTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new CategoriaTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new KitTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new ContaTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new ItemTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new SubItemTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new PedidoTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new PedidoSubItemTableDefinition().onUpgrade(db, oldVersion, newVersion);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}