package com.login.beachstop.garcom.android.managers.sqlite;

import com.login.beachstop.garcom.android.models.SubItem;

import org.droidpersistence.dao.TableDefinition;

public class SubItemTableDefinition extends TableDefinition<SubItem> {

    public SubItemTableDefinition() {
        super(SubItem.class);
    }
}