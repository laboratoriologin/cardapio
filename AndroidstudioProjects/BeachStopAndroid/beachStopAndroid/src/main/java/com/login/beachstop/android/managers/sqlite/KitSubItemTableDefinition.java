package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.KitSubItem;

import org.droidpersistence.dao.TableDefinition;

public class KitSubItemTableDefinition extends TableDefinition<KitSubItem> {

    public KitSubItemTableDefinition() {
        super(KitSubItem.class);
    }
}