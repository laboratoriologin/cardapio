package com.login.beachstop.garcom.android.managers.sqlite;

import com.login.beachstop.garcom.android.models.Item;

import org.droidpersistence.dao.TableDefinition;

public class ItemTableDefinition extends TableDefinition<Item> {

    public ItemTableDefinition() {
        super(Item.class);
    }
}