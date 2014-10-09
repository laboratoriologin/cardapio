package com.login.android.cardapio.model;

import java.io.Serializable;

import android.util.SparseArray;

import com.login.android.cardapio.R;
import com.login.android.cardapio.fragment.ListaItemCardapioFragment;
import com.login.android.cardapio.fragment.ListaTodosItemCardapioFragment;

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
		this.listaItemMenu.put(1, new CategoriaCardapioItemSys(1l, "Bebidas", R.drawable.bt_home_bebidas, R.drawable.topo_bebidas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(2, new CategoriaCardapioItemSys(2l, "Entradas", R.drawable.bt_home_entradas, R.drawable.topo_entradas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(3, new CategoriaCardapioItemSys(3l, "Saladas", R.drawable.bt_home_saladas, R.drawable.topo_saladas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(4, new CategoriaCardapioItemSys(4l, "Sugestão do Chefe", R.drawable.bt_home_sugestao_chef, R.drawable.topo_sugestao_chef, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(5, new CategoriaCardapioItemSys(5l, "Massas", R.drawable.bt_home_massas, R.drawable.topo_massas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(6, new CategoriaCardapioItemSys(6l, "Grelhados", R.drawable.bt_home_grelhados, R.drawable.topo_grelhados, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(7, new CategoriaCardapioItemSys(7l, "Comida Oriental", R.drawable.bt_home_comida_oriental, R.drawable.topo_comida_oriental, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(8, new CategoriaCardapioItemSys(8l, "Sobremesas", R.drawable.bt_home_sobremesas, R.drawable.topo_sobremesas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(0, new CategoriaCardapioItemSys(0l, "Todos os pratos", R.drawable.bt_home_todos_pratos, R.drawable.topo_todos_pratos, ListaTodosItemCardapioFragment.class));
	}

	public CategoriaCardapioItemSys getItemMenu(Long key) {
		return this.listaItemMenu.get(key.intValue());
	}
}
