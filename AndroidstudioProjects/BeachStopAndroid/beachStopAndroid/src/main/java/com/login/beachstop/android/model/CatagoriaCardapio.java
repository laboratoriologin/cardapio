package com.login.beachstop.android.model;

import java.io.Serializable;

import android.util.SparseArray;

import com.login.beachstop.android.R;
import com.login.beachstop.android.fragment.ListaItemCardapioFragment;
import com.login.beachstop.android.fragment.ListaTodosItemCardapioFragment;

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
		this.listaItemMenu.put(14, new CategoriaCardapioItemSys(14l, "Pastéis", R.drawable.bt_home_pasteis, R.drawable.icone_topo_pasteis, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(1, new CategoriaCardapioItemSys(1l, "Bebidas", R.drawable.bt_home_bebidas, R.drawable.icone_topo_bebidas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(2, new CategoriaCardapioItemSys(2l, "Petiscos", R.drawable.bt_home_petiscos, R.drawable.icone_topo_entradas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(3, new CategoriaCardapioItemSys(3l, "Saladas", R.drawable.bt_home_saladas, R.drawable.icone_topo_saladas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(4, new CategoriaCardapioItemSys(4l, "Acompanhamentos", R.drawable.bt_home_acompanhamentos, R.drawable.icone_topo_acompanhamentos, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(5, new CategoriaCardapioItemSys(5l, "Pizzas", R.drawable.bt_home_pizza, R.drawable.icone_topo_pizza, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(6, new CategoriaCardapioItemSys(6l, "Pratos do mar", R.drawable.bt_home_pratos_do_mar, R.drawable.icone_topo_pratos_do_mar, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(7, new CategoriaCardapioItemSys(7l, "Pratos da terra", R.drawable.bt_home_pratos_da_terra, R.drawable.icone_topo_grelhados, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(8, new CategoriaCardapioItemSys(8l, "Sobremesas", R.drawable.bt_home_sobremesas, R.drawable.icone_topo_sobremesas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(9, new CategoriaCardapioItemSys(9l, "Comida oriental", R.drawable.bt_home_comida_oriental, R.drawable.icone_topo_comida_oriental, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(10, new CategoriaCardapioItemSys(10l, "Diversos", R.drawable.bt_home_diversos, R.drawable.icone_topo_diversos, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(11, new CategoriaCardapioItemSys(11l, "Massas", R.drawable.bt_home_massas, R.drawable.icone_topo_massas, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(13, new CategoriaCardapioItemSys(13l, "Sugestão do chef", R.drawable.bt_home_sugestao_chef, R.drawable.icone_topo_sugestao_chef, ListaItemCardapioFragment.class));
		this.listaItemMenu.put(0, new CategoriaCardapioItemSys(0l, "Todos os pratos", R.drawable.bt_home_todos_pratos, R.drawable.icone_topo_todos_pratos, ListaTodosItemCardapioFragment.class));
		
	}

	public CategoriaCardapioItemSys getItemMenu(Long key) {
		return this.listaItemMenu.get(key.intValue());
	}
}
