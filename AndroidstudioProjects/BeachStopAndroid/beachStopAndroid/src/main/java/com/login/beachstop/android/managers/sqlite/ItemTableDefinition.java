package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.Item;

import org.droidpersistence.dao.TableDefinition;

public class ItemTableDefinition extends TableDefinition<Item> {

    public ItemTableDefinition() {
        super(Item.class);
    }
}