package com.login.beachstop.garcom.android.managers.sqlite.dao;

import com.login.beachstop.garcom.android.models.Empresa;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

/**
 * Created by Argus on 17/12/2014.
 */
public class EmpresaDAO extends DroidDao<Empresa, Long> {

    protected DataManager dataManager;

    public EmpresaDAO(TableDefinition<Empresa> tableDefinition, DataManager dataManager) {
        super(Empresa.class, tableDefinition, dataManager.getDatabase());
        this.dataManager = dataManager;
    }

    public Empresa get() {

        List<Empresa> listEmpresa = this.getAll();

        if (listEmpresa.size() == 0) {
            return null;
        } else {
            return listEmpresa.get(0);
        }
    }

    public boolean hasEmpresa() {

        List<Empresa> listEmpresa = this.getAll();

        if (listEmpresa.size() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
