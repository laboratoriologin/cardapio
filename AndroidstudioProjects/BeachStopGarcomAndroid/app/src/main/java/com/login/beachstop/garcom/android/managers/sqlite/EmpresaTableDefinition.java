package com.login.beachstop.garcom.android.managers.sqlite;

import com.login.beachstop.garcom.android.models.Empresa;

import org.droidpersistence.dao.TableDefinition;

/**
 * Created by Argus on 17/12/2014.
 */
public class EmpresaTableDefinition extends TableDefinition<Empresa> {
    public EmpresaTableDefinition() {
        super(Empresa.class);
    }
}
