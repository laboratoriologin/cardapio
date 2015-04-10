package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.Conta;

import org.droidpersistence.dao.TableDefinition;

public class ContaTableDefinition extends TableDefinition<Conta> {

    public ContaTableDefinition() {
        super(Conta.class);
    }
}