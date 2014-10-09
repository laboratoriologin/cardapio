package com.login.beachstop.android.garcom.sqlite.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.beachstop.android.garcom.exception.PersistException;
import com.login.beachstop.android.garcom.model.CategoriaCardapioItem;
import com.login.beachstop.android.garcom.model.ItemCardapio;
import com.login.beachstop.android.garcom.model.ItemCardapioSubItem;
import com.login.beachstop.android.garcom.sqlite.CategoriaCardapioItemTableDefinition;
import com.login.beachstop.android.garcom.sqlite.ChaveCardapioEmpresaTableDefinition;
import com.login.beachstop.android.garcom.sqlite.ItemCardapioSubItemTableDefinition;
import com.login.beachstop.android.garcom.sqlite.ItemCardapioTableDefinition;
import com.login.beachstop.android.garcom.sqlite.OpenHelper;

public class DataManager {

	private SQLiteDatabase database;
	private CategoriaCardapioItemDAO categoriaCardapioItemDAO;
	private ChaveCardapioEmpresaDAO chaveCardapioEmpresaDAO;
	private ItemCardapioDAO itemCardapioDAO;
	private ItemCardapioSubItemDAO itemCardapioSubItemDAO;

	public DataManager(Context context) {

		SQLiteOpenHelper openHelper = new OpenHelper(context, "garcom", null, 1);
		this.database = openHelper.getWritableDatabase();

		this.setCategoriaCardapioItemDAO(new CategoriaCardapioItemDAO(new CategoriaCardapioItemTableDefinition(), database));
		this.setChaveCardapioEmpresaDAO(new ChaveCardapioEmpresaDAO(new ChaveCardapioEmpresaTableDefinition(), database));
		this.setItemCardapioDAO(new ItemCardapioDAO(new ItemCardapioTableDefinition(), database));
		this.setItemCardapioSubItemDAO(new ItemCardapioSubItemDAO(new ItemCardapioSubItemTableDefinition(), database));
	}

	public List<ItemCardapio> getAll(Long idCategoria) {
		List<ItemCardapio> listItemCardapio = this.itemCardapioDAO.getAllbyClause("ID_CATEGORIA_CARDAPIO=?", new String[] { idCategoria.toString() }, null, null, null);

		List<ItemCardapioSubItem> listSubItemCardapio;
		for (ItemCardapio itemCardapio : listItemCardapio) {
			listSubItemCardapio = this.itemCardapioSubItemDAO.getAllbyClause("ID_ITEM_CARDAPIO=?", new String[] { itemCardapio.getId().toString() }, null, null, null);
			itemCardapio.setSubItens(listSubItemCardapio);
		}
		return listItemCardapio;
	}

	public List<ItemCardapioSubItem> getAll(List<CategoriaCardapioItem> listCategoriaCardapioItem) {
		List<ItemCardapioSubItem> listItemCardapioSubItem = new ArrayList<ItemCardapioSubItem>();
		List<ItemCardapio> listItemCardapio = new ArrayList<ItemCardapio>();

		for (CategoriaCardapioItem categoriaCardapioItem : listCategoriaCardapioItem) {
			listItemCardapio.addAll(this.itemCardapioDAO.getAllbyClause("ID_CATEGORIA_CARDAPIO=?", new String[] { categoriaCardapioItem.getId().toString() }, null, null, null));
		}

		for (ItemCardapio itemCardapio : listItemCardapio) {
			itemCardapio.setSubItens(this.itemCardapioSubItemDAO.getAllbyClause("ID_ITEM_CARDAPIO=?", new String[] { itemCardapio.getId().toString() }, null, null, null));

			for (ItemCardapioSubItem item : itemCardapio.getSubItens()) {
				item.setItemCardapio(itemCardapio);
				listItemCardapioSubItem.add(item);
			}

		}	
		
		Collections.sort(listItemCardapioSubItem);
		return listItemCardapioSubItem;
	}

	public long save(ItemCardapioSubItem subItemCardapio) throws PersistException {
		long result = 0;

		try {
			result = this.itemCardapioSubItemDAO.save(subItemCardapio);

		} catch (Exception ex) {

			throw new PersistException(ex);

		}

		return result;
	}

	public long save(ItemCardapio itemCardapio) throws PersistException {
		long result = 0;

		try {

			this.database.beginTransaction();

			result = this.getItemCardapioDAO().save(itemCardapio);

			for (ItemCardapioSubItem subItemCardapio : itemCardapio.getSubItens()) {
				save(subItemCardapio);
			}

			this.database.setTransactionSuccessful();

		} catch (Exception ex) {

			throw new PersistException(ex);

		} finally {

			this.database.endTransaction();
		}

		return result;
	}

	/**
	 * @return the database
	 */
	public SQLiteDatabase getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	/**
	 * @return the categoriaCardapioItemDAO
	 */
	public CategoriaCardapioItemDAO getCategoriaCardapioItemDAO() {
		return categoriaCardapioItemDAO;
	}

	/**
	 * @param categoriaCardapioItemDAO
	 *            the categoriaCardapioItemDAO to set
	 */
	public void setCategoriaCardapioItemDAO(CategoriaCardapioItemDAO categoriaCardapioItemDAO) {
		this.categoriaCardapioItemDAO = categoriaCardapioItemDAO;
	}

	/**
	 * @return the chaveCardapioEmpresaDAO
	 */
	public ChaveCardapioEmpresaDAO getChaveCardapioEmpresaDAO() {
		return chaveCardapioEmpresaDAO;
	}

	/**
	 * @param chaveCardapioEmpresaDAO
	 *            the chaveCardapioEmpresaDAO to set
	 */
	public void setChaveCardapioEmpresaDAO(ChaveCardapioEmpresaDAO chaveCardapioEmpresaDAO) {
		this.chaveCardapioEmpresaDAO = chaveCardapioEmpresaDAO;
	}

	/**
	 * @return the itemCardapioSubItemDAO
	 */
	public ItemCardapioSubItemDAO getItemCardapioSubItemDAO() {
		return itemCardapioSubItemDAO;
	}

	/**
	 * @param itemCardapioSubItemDAO
	 *            the itemCardapioSubItemDAO to set
	 */
	public void setItemCardapioSubItemDAO(ItemCardapioSubItemDAO itemCardapioSubItemDAO) {
		this.itemCardapioSubItemDAO = itemCardapioSubItemDAO;
	}

	/**
	 * @return the itemCardapioDAO
	 */
	public ItemCardapioDAO getItemCardapioDAO() {
		return itemCardapioDAO;
	}

	/**
	 * @param itemCardapioDAO
	 *            the itemCardapioDAO to set
	 */
	public void setItemCardapioDAO(ItemCardapioDAO itemCardapioDAO) {
		this.itemCardapioDAO = itemCardapioDAO;
	}

}
