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

import com.login.cardapio.model.Empresa;
import com.login.cardapio.model.EmpresaCategoriaCardapio;
import com.login.cardapio.model.Item;
import com.login.cardapio.model.SubItem;
import com.login.cardapio.model.TiposQuantidade;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.UsuarioUtil;
import com.login.cardapio.util.Utilitarios;

@ViewScoped
@ManagedBean(name = "itemFaces")
public class ItemFaces extends CrudFaces<Item> {

	private List<SelectItem> comboEmpresaCategoriaCardapio;
	private List<SelectItem> comboQTDS;
	private SubItem subItemSelecionado;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("id");

		this.subItemSelecionado = new SubItem();
		this.subItemSelecionado.setTipoQuantidade(new TiposQuantidade());
		this.comboEmpresaCategoriaCardapio = super.initCombo(new EmpresaCategoriaCardapio().findByEmpresa(UsuarioUtil.obterUsuarioConectado().getEmpresa()), "id", "categoriaCardapio.descricao");
		this.comboQTDS = super.initCombo(new TiposQuantidade().findAll("id"), "id", "descricao");
	}

	public void uploadMidias(FileUploadEvent event) {

		this.getCrudModel().setImagem(Utilitarios.gerarNomeArquivo() + "." + FilenameUtils.getExtension(event.getFile().getFileName()));

		CardapioUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO + this.getCrudModel().getImagem());
	}

	@Override
	protected String find() {
		this.setGrid(getCrudPesquisaModel().findByCategoria());
		return null;
	}

	public String limpar() {

		super.limpar();

		getCrudModel().setEmpresaCategoriaCardapio(new EmpresaCategoriaCardapio());

		this.subItemSelecionado = new SubItem();

		this.subItemSelecionado.setTipoQuantidade(new TiposQuantidade());

		return null;
	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setEmpresaCategoriaCardapio(new EmpresaCategoriaCardapio());
		return null;
	}

	@Override
	protected void posPersist() throws TSApplicationException {
		Empresa empresa = UsuarioUtil.obterUsuarioConectado().getEmpresa();
		empresa = empresa.getById();
		empresa.setKeyCardapio(Utilitarios.gerarNomeArquivo());
		empresa.update();

	}

	public void addSubItem() {
		SubItem subitem = new SubItem();

		if (TSUtil.isEmpty(subItemSelecionado.getDescricao())) {
			CardapioUtil.addErrorMessage("Descrição: Obrigatório");
			return;
		}

		if (TSUtil.tratarLong(subItemSelecionado.getTipoQuantidade().getId()) == null) {
			CardapioUtil.addErrorMessage("Tipo de quantidade: Obrigatório");
			return;
		}

		if (TSUtil.isEmpty(subItemSelecionado.getCodSubItem())) {
			CardapioUtil.addErrorMessage("Código do item: Obrigatório");
			return;
		}

		subitem.setDescricao(subItemSelecionado.getDescricao());
		subitem.setQuantidade(subItemSelecionado.getQuantidade());
		subitem.setTipoQuantidade(subItemSelecionado.getTipoQuantidade().getById());
		subitem.setValor(subItemSelecionado.getValor());
		subitem.setCodSubItem(subItemSelecionado.getCodSubItem());
		subitem.setItem(this.getCrudModel());

		if (TSUtil.isEmpty(getCrudModel().getSubItens())) {
			getCrudModel().setSubItens(new ArrayList<SubItem>());
		}

		if (!this.getCrudModel().getSubItens().contains(subitem)) {

			this.getCrudModel().getSubItens().add(subitem);
		
		} else {

			CardapioUtil.addErrorMessage("Esse subitem já foi adicionado");
		}

		this.subItemSelecionado = new SubItem();
		this.subItemSelecionado.setTipoQuantidade(new TiposQuantidade());
	}

	public void delSubItem() {
		getCrudModel().getSubItens().remove(this.subItemSelecionado);
	}

	public String limparSubItem() {
		this.subItemSelecionado = new SubItem();

		return null;
	}

	public List<SelectItem> getComboEmpresaCategoriaCardapio() {
		return comboEmpresaCategoriaCardapio;
	}

	public void setComboEmpresaCategoriaCardapio(List<SelectItem> comboEmpresaCategoriaCardapio) {
		this.comboEmpresaCategoriaCardapio = comboEmpresaCategoriaCardapio;
	}

	public List<SelectItem> getComboQTDS() {
		return comboQTDS;
	}

	public void setComboQTDS(List<SelectItem> comboQTDS) {
		this.comboQTDS = comboQTDS;
	}

	public SubItem getSubItemSelecionado() {
		return subItemSelecionado;
	}

	public void setSubItemSelecionado(SubItem subItemSelecionado) {
		this.subItemSelecionado = subItemSelecionado;
	}
}
