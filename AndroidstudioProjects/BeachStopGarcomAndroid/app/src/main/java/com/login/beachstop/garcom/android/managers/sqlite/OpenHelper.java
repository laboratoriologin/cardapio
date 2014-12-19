package com.login.beachstop.garcom.android.managers.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Argus on 16/12/2014.
 */
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            new EmpresaTableDefinition().onCreate(db);
            new CategoriaTableDefinition().onCreate(db);
            new ItemTableDefinition().onCreate(db);
            new KitTableDefinition().onCreate(db);
            new SubItemTableDefinition().onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            new EmpresaTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new CategoriaTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new ItemTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new KitTableDefinition().onUpgrade(db, oldVersion, newVersion);
            new SubItemTableDefinition().onUpgrade(db, oldVersion, newVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
