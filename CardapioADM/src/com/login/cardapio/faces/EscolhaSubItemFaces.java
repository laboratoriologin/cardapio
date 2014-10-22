package com.login.cardapio.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Categoria;
import com.login.cardapio.model.Item;
import com.login.cardapio.model.SubItem;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EscolhaSubItemFaces extends TSMainFaces {

	private List<SubItem> listSubItem;
	private List<SelectItem> comboCategoria;
	private List<SelectItem> comboItem;
	private Categoria categoriaSelecionada;
	private Item itemSelecionado;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();
		this.comboCategoria = super.initCombo(new Categoria().findAll("descricao"), "id", "descricao");
		this.categoriaSelecionada = new Categoria();
		this.itemSelecionado = new Item();
		this.listSubItem = new ArrayList<SubItem>();

	}

	@Override
	protected String update() throws TSApplicationException {

		for (SubItem subItem : this.listSubItem) {
			subItem.update();
		}

		this.clearFields();

		return null;

	}

	public void findItem() {

		List<Item> listItem = new ArrayList<Item>();
		this.listSubItem = new ArrayList<SubItem>();	

		if (this.categoriaSelecionada.getId() != 0) {

			Item itemBusca = new Item();
			itemBusca.setCategoria(categoriaSelecionada);

			listItem = itemBusca.findByCategoria();

		}

		this.comboItem = super.initCombo(listItem, "id", "descricao");
	}
	
	public void findSubItem() {
		
		this.listSubItem = new ArrayList<SubItem>();		
	
		if (!TSUtil.isEmpty(this.itemSelecionado.getId())) {
			
			SubItem subItemBusca = new SubItem();
			subItemBusca.setItem(itemSelecionado);
			
			this.listSubItem = subItemBusca.findByItem();
			
		}
		
	}

	public List<SubItem> getListSubItem() {
		return listSubItem;
	}

	public void setListSubItem(List<SubItem> listSubItem) {
		this.listSubItem = listSubItem;
	}

	public List<SelectItem> getComboCategoria() {
		return comboCategoria;
	}

	public void setComboCategoria(List<SelectItem> comboCategoria) {
		this.comboCategoria = comboCategoria;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public List<SelectItem> getComboItem() {
		return comboItem;
	}

	public void setComboItem(List<SelectItem> comboItem) {
		this.comboItem = comboItem;
	}

	public Item getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(Item itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

}
