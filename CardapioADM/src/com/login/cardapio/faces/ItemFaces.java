package com.login.cardapio.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.Categoria;
import com.login.cardapio.model.Item;
import com.login.cardapio.model.SubItem;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.Utilitarios;

@ViewScoped
@ManagedBean(name = "itemFaces")
public class ItemFaces extends CrudFaces<Item> {

	private static final long serialVersionUID = 1L;
	private List<SelectItem> comboCategoria;
	private SubItem subItemSelecionado;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("id");

		this.subItemSelecionado = new SubItem();
		this.subItemSelecionado.setFlagAtivo(true);
		this.comboCategoria = super.initCombo(new Categoria().findAll("descricao"), "id", "descricao");
		
		getCrudModel().setFlagAtivo(true);

	}
	
	@Override
	protected void prePersist() {
		// TODO Auto-generated method stub
		super.prePersist();
		
		try {
			Utilitarios.gerarNovoCodigoCardapio();
		} catch (TSApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage("Erro no sistema, entre em contato com o administrador, Erro: 0101!");
		}
	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setCategoria(new Categoria());
		return null;
	}

	@Override
	protected String find() {
		this.setGrid(getCrudPesquisaModel().findByCategoria());
		return null;
	}

	public String limpar() {

		super.limpar();

		getCrudModel().setCategoria(new Categoria());
		getCrudModel().setFlagAtivo(true);
		
		this.subItemSelecionado = new SubItem();
		this.subItemSelecionado.setFlagAtivo(true);		

		return null;
	}

	public void uploadMidias(FileUploadEvent event) {

		this.getCrudModel().setImagem(Utilitarios.gerarNomeArquivo() + "." + FilenameUtils.getExtension(event.getFile().getFileName()));

		CardapioUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO + this.getCrudModel().getImagem());
	}

	public void addSubItem() {
		SubItem subitem = new SubItem();

		if (TSUtil.isEmpty(subItemSelecionado.getNome())) {
			CardapioUtil.addErrorMessage("Nome: Obrigatório");
			return;
		}

		if (TSUtil.isEmpty(subItemSelecionado.getDescricao())) {
			CardapioUtil.addErrorMessage("Descrição: Obrigatório");
			return;
		}

		if (TSUtil.isEmpty(subItemSelecionado.getCodigo())) {
			CardapioUtil.addErrorMessage("Código do item: Obrigatório");
			return;
		}

		if (TSUtil.isEmpty(subItemSelecionado.getOrdem())) {
			CardapioUtil.addErrorMessage("Ordem: Obrigatório");
			return;
		}

		subitem.setNome(subItemSelecionado.getNome());
		subitem.setValor(subItemSelecionado.getValor());
		subitem.setDescricao(subItemSelecionado.getDescricao());
		subitem.setItem(this.getCrudModel());
		subitem.setCodigo(subItemSelecionado.getCodigo());
		subitem.setOrdem(subItemSelecionado.getOrdem());
		subitem.setFlagAtivo(subItemSelecionado.getFlagAtivo());

		if (TSUtil.isEmpty(getCrudModel().getSubItens())) {
			getCrudModel().setSubItens(new ArrayList<SubItem>());
		}

		if (!this.getCrudModel().getSubItens().contains(subitem)) {

			this.getCrudModel().getSubItens().add(subitem);

		} else {

			CardapioUtil.addErrorMessage("Esse subitem já foi adicionado");
		}

		this.subItemSelecionado = new SubItem();
		this.subItemSelecionado.setFlagAtivo(true);
	}

	public void delSubItem() {
		getCrudModel().getSubItens().remove(this.subItemSelecionado);
	}

	public String limparSubItem() {
		this.subItemSelecionado = new SubItem();
		this.subItemSelecionado.setFlagAtivo(true);
		return null;
	}

	public List<SelectItem> getComboCategoria() {
		return comboCategoria;
	}

	public void setComboCategoria(List<SelectItem> comboCategoria) {
		this.comboCategoria = comboCategoria;
	}

	public SubItem getSubItemSelecionado() {
		return subItemSelecionado;
	}

	public void setSubItemSelecionado(SubItem subItemSelecionado) {
		this.subItemSelecionado = subItemSelecionado;
	}
}
