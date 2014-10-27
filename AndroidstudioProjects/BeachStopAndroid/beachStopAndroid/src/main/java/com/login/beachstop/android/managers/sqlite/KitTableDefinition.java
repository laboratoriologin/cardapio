package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.Kit;

import org.droidpersistence.dao.TableDefinition;

public class KitTableDefinition extends TableDefinition<Kit> {

    public KitTableDefinition() {
        super(Kit.class);
    }
}