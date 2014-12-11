package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.Categoria;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class CategoriaDAO extends DroidDao<Categoria, Long> {

    protected DataManager dataManager;

    public CategoriaDAO(TableDefinition<Categoria> tableDefinition, DataManager dataManager) {
        super(Categoria.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public List<Categoria> getAllOrderByOrdem(){
        return this.getAllbyClause("", null, "", "", "ORDEM");
    }

    public int getQtdCategoria() {

        List<Categoria> categorias = this.getAll();

        return categorias.size();

    }

    public void save(List<Categoria> categorias) throws Exception {

        for (Categoria categoria : categorias) {
            this.save(categoria);
        }

    }

}
