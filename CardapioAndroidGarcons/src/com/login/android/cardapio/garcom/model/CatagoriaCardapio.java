package com.login.android.cardapio.garcom.model;

import java.io.Serializable;

import android.util.SparseArray;

import com.login.android.cardapio.garcom.PedidoActivity;

public class CatagoriaCardapio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SparseArray<CategoriaCardapioItemSys> listaItemMenu;

	public CatagoriaCardapio() {
		this.listaItemMenu = new SparseArray<CategoriaCardapioItemSys>();
		this.loadItemMenu();
	}

	private void loadItemMenu() {
		this.listaItemMenu.put(1, new CategoriaCardapioItemSys(1l, "Bebidas", PedidoActivity.class));
		this.listaItemMenu.put(2, new CategoriaCardapioItemSys(2l, "Entradas", PedidoActivity.class));
		this.listaItemMenu.put(3, new CategoriaCardapioItemSys(3l, "Saladas", PedidoActivity.class));
		this.listaItemMenu.put(4, new CategoriaCardapioItemSys(4l, "Sugestão do Chefe", PedidoActivity.class));
		this.listaItemMenu.put(5, new CategoriaCardapioItemSys(5l, "Massas", PedidoActivity.class));
		this.listaItemMenu.put(6, new CategoriaCardapioItemSys(6l, "Grelhados", PedidoActivity.class));
		this.listaItemMenu.put(7, new CategoriaCardapioItemSys(7l, "Comida Oriental", PedidoActivity.class));
		this.listaItemMenu.put(8, new CategoriaCardapioItemSys(8l, "Sobremesas", PedidoActivity.class));
		this.listaItemMenu.put(0, new CategoriaCardapioItemSys(0l, "Todos os pratos", PedidoActivity.class));
	}

	public CategoriaCardapioItemSys getItemMenu(Long key) {
		return this.listaItemMenu.get(key.intValue());
	}
}
