package com.login.beachstop.android.sqlite;

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
			new ItemCardapioTableDefinition().onCreate(db);
			new ItemCardapioSubItemTableDefinition().onCreate(db);
			new CategoriaCardapioItemTableDefinition().onCreate(db);
			new ChaveCardapioEmpresaTableDefinition().onCreate(db);
			new ContaTableDefinition().onCreate(db);
			new PedidoTableDefinition().onCreate(db);
			new PedidoItemTableDefinition().onCreate(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			new ItemCardapioTableDefinition().onUpgrade(db, oldVersion, newVersion);
			new ItemCardapioSubItemTableDefinition().onUpgrade(db, oldVersion, newVersion);
			new CategoriaCardapioItemTableDefinition().onUpgrade(db, oldVersion, newVersion);
			new ChaveCardapioEmpresaTableDefinition().onUpgrade(db, oldVersion, newVersion);
			new ContaTableDefinition().onUpgrade(db, oldVersion, newVersion);
			new PedidoTableDefinition().onUpgrade(db, oldVersion, newVersion);
			new PedidoItemTableDefinition().onUpgrade(db, oldVersion, newVersion);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}