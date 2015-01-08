package com.login.beachstop.garcom.android.managers.sqlite;

import com.login.beachstop.garcom.android.models.KitSubItem;

import org.droidpersistence.dao.TableDefinition;

public class KitSubItemTableDefinition extends TableDefinition<KitSubItem> {

    public KitSubItemTableDefinition() {
        super(KitSubItem.class);
    }
}