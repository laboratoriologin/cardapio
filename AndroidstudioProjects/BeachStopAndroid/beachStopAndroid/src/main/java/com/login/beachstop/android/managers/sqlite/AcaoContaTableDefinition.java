package com.login.beachstop.android.managers.sqlite;

import com.login.beachstop.android.models.AcaoConta;
import org.droidpersistence.dao.TableDefinition;

public class AcaoContaTableDefinition extends TableDefinition<AcaoConta> {
    public AcaoContaTableDefinition() {
        super(AcaoConta.class);
    }
}