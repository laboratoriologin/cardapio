package com.login.beachstop.android.sqlite.dao;

import java.util.List;

import org.droidpersistence.annotation.Table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.ItemCardapioSubItem;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.sqlite.CategoriaCardapioItemTableDefinition;
import com.login.beachstop.android.sqlite.ChaveCardapioEmpresaTableDefinition;
import com.login.beachstop.android.sqlite.ContaTableDefinition;
import com.login.beachstop.android.sqlite.ItemCardapioSubItemTableDefinition;
import com.login.beachstop.android.sqlite.ItemCardapioTableDefinition;
import com.login.beachstop.android.sqlite.OpenHelper;
import com.login.beachstop.android.sqlite.PedidoItemTableDefinition;
import com.login.beachstop.android.sqlite.PedidoTableDefinition;
import com.login.beachstop.android.sqlite.exception.PersistException;

public class DataManager {

	private SQLiteDatabase database;
	private ItemCardapioSubItemDAO itemCardapioSubItemDAO;
	private ItemCardapioDAO itemCardapioDAO;
	private CategoriaCardapioItemDAO categoriaCardapioItemDAO;
	private ChaveCardapioEmpresaDAO chaveCardapioEmpresaDAO;
	private PedidoDAO pedidoDAO;
	private PedidoItemDAO pedidoItemDAO;
	private ContaDAO contaDAO;

	public DataManager(Context context) {

		SQLiteOpenHelper openHelper = new OpenHelper(context, "cardapio", null, 1);
		this.database = openHelper.getWritableDatabase();
		this.itemCardapioSubItemDAO = new ItemCardapioSubItemDAO(new ItemCardapioSubItemTableDefinition(), database);
		this.itemCardapioDAO = new ItemCardapioDAO(new ItemCardapioTableDefinition(), database);
		this.categoriaCardapioItemDAO = new CategoriaCardapioItemDAO(new CategoriaCardapioItemTableDefinition(), database);
		this.chaveCardapioEmpresaDAO = new ChaveCardapioEmpresaDAO(new ChaveCardapioEmpresaTableDefinition(), database);
		this.contaDAO = new ContaDAO(new ContaTableDefinition(), database);
		this.pedidoDAO = new PedidoDAO(new PedidoTableDefinition(), database);
		this.pedidoItemDAO = new PedidoItemDAO(new PedidoItemTableDefinition(), database);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long getNextId(Class c) {

		Table annotation = (Table) c.getAnnotation(Table.class);

		final Cursor cursor = this.database.rawQuery("SELECT MAX(ID)  FROM " + annotation.name() + ";", null);
		Long id = 0l;
		if (cursor != null) {
			try {
				if (cursor.moveToFirst()) {
					id = cursor.getLong(0);
				}
			} catch (Exception e) {
				return null;
			} finally {
				cursor.close();
			}
		} else {
			return null;
		}

		id++;

		return id;
	}

	public Pedido getPedido(Boolean finalizado) {

		String strFinalizado = "0";

		if (finalizado)
			strFinalizado = "1";

		Pedido pedido = this.pedidoDAO.getByClause("FINALIZADO =" + strFinalizado, null);
		if (pedido == null) {
			return null;
		} else {
			List<PedidoItem> listPedidoItem = this.pedidoItemDAO.getAllbyClause("ID_PEDIDO = ?", new String[] { pedido.getId().toString() }, null, null, null);
			pedido.setListPedidoItem(listPedidoItem);

			for (PedidoItem pedidoItem : pedido.getListPedidoItem()) {
				pedidoItem.setSubItem(this.itemCardapioSubItemDAO.get(pedidoItem.getIdSubItem()));
			}

			return pedido;
		}
	}

	public Conta getConta() {
		List<Conta> listConta = this.contaDAO.getAll();

		if (listConta.size() == 0) {
			return null;
		} else {
			return listConta.get(0);
		}
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

	public Boolean save(List<PedidoItem> listPedidoItem) {
		try {
			for (PedidoItem pedidoItem : listPedidoItem) {
				this.pedidoItemDAO.save(pedidoItem);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public ItemCardapioSubItemDAO getItemCardapioSubItemDAO() {
		return itemCardapioSubItemDAO;
	}

	public void setItemCardapioSubItemDAO(ItemCardapioSubItemDAO obj) {
		this.itemCardapioSubItemDAO = obj;
	}

	public ItemCardapioDAO getItemCardapioDAO() {
		return itemCardapioDAO;
	}

	public void setItemCardapioDAO(ItemCardapioDAO itemCardapioDAO) {
		this.itemCardapioDAO = itemCardapioDAO;
	}

	public CategoriaCardapioItemDAO getCategoriaCardapioItemDAO() {
		return categoriaCardapioItemDAO;
	}

	public void setCategoriaCardapioItemDAO(CategoriaCardapioItemDAO obj) {
		this.categoriaCardapioItemDAO = obj;
	}

	public ChaveCardapioEmpresaDAO getChaveCardapioEmpresaDAO() {
		return chaveCardapioEmpresaDAO;
	}

	public void setChaveCardapioEmpresaDAO(ChaveCardapioEmpresaDAO chaveCardapioEmpresaDAO) {
		this.chaveCardapioEmpresaDAO = chaveCardapioEmpresaDAO;
	}

	public ContaDAO getContaDAO() {
		return contaDAO;
	}

	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}

	public PedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(PedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

	public PedidoItemDAO getPedidoItemDAO() {
		return pedidoItemDAO;
	}

	public void setPedidoItemDAO(PedidoItemDAO pedidoItemDAO) {
		this.pedidoItemDAO = pedidoItemDAO;
	}
}
