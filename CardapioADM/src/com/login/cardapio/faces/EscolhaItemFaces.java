package com.login.cardapio.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Categoria;
import com.login.cardapio.model.Item;
import com.login.cardapio.util.Utilitarios;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EscolhaItemFaces extends TSMainFaces {

	private List<Item> listItem;
	private List<SelectItem> comboCategoria;
	private Categoria categoriaSelecionada;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();
		this.comboCategoria = super.initCombo(new Categoria().findAll("descricao"), "id", "descricao");
		this.categoriaSelecionada = new Categoria();
		this.listItem = new ArrayList<Item>();

	}
	
	@Override
	protected String update() throws TSApplicationException {

		try {
			Utilitarios.gerarNovoCodigoCardapio();
		} catch (TSApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage("Erro no sistema, entre em contato com o administrador, Erro: 0101!");
		}
		
		for (Item item : this.listItem) {
			item.update();
		}

		this.clearFields();

		return null;

	}

	public void findItem() {

		if (this.categoriaSelecionada.getId() != 0) {

			Item itemBusca = new Item();
			itemBusca.setCategoria(categoriaSelecionada);

			this.listItem = itemBusca.findByCategoria();
			
		} else {
			this.listItem = new ArrayList<Item>();
		}

	}

	public List<Item> getListItem() {
		return listItem;
	}

	public void setListItem(List<Item> listItem) {
		this.listItem = listItem;
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

}
