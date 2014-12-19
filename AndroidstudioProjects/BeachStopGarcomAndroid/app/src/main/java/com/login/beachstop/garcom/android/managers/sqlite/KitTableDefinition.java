package com.login.beachstop.garcom.android.managers.sqlite;

import com.login.beachstop.garcom.android.models.Kit;

import org.droidpersistence.dao.TableDefinition;

public class KitTableDefinition extends TableDefinition<Kit> {

    public KitTableDefinition() {
        super(Kit.class);
    }
}