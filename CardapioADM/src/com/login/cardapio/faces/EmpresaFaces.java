package com.login.cardapio.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.CategoriaCardapio;
import com.login.cardapio.model.Empresa;
import com.login.cardapio.model.EmpresaCategoriaCardapio;
import com.login.cardapio.model.Menu;
import com.login.cardapio.model.Metrica;
import com.login.cardapio.model.Permissao;
import com.login.cardapio.model.TipoAlerta;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.UsuarioUtil;

@ViewScoped
@ManagedBean(name = "empresaFaces")
public class EmpresaFaces extends CrudFaces<Empresa> {

	private List<SelectItem> comboMenus;
	private Permissao permissaoSelecionada;
	private boolean admLogado = true;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboMenus = super.initCombo(new Menu().pesquisarExecutaveis(), "id", "descricao");
		setFieldOrdem("descricao");
	}

	@Override
	protected void preInsert() {

		super.preInsert();

		this.getCrudModel().setCategorias(new ArrayList<EmpresaCategoriaCardapio>());

		EmpresaCategoriaCardapio categoria = null;

		for (CategoriaCardapio cardapio : new CategoriaCardapio().findAll("descricao")) {

			categoria = new EmpresaCategoriaCardapio();

			categoria.setEmpresa(this.getCrudModel());

			categoria.setCategoriaCardapio(cardapio);

			categoria.setOrdem(1);

			categoria.setFlagAtivo(false);

			categoria.setPesoMetrica(1);

			this.getCrudModel().getCategorias().add(categoria);

		}

	}

	public String limpar() {

		super.limpar();

		this.getCrudModel().setPermissoes(new ArrayList<Permissao>());

		if (!Constantes.GRUPO_ADMINISTRADOR.equals(UsuarioUtil.obterUsuarioConectado().getEmpresa().getId())) {

			admLogado = false;

			this.setCrudModel(UsuarioUtil.obterUsuarioConectado().getEmpresa());

			this.detail();

		}

		this.getCrudModel().setMetricas(new ArrayList<Metrica>());
		Metrica metrica;
		List<TipoAlerta> ta = new TipoAlerta().findAll("id");
		for (TipoAlerta tipoAlerta : ta) {
			metrica = new Metrica();
			metrica.setEmpresa(UsuarioUtil.obterUsuarioConectado().getEmpresa());
			metrica.setTipoAlerta(tipoAlerta);
			this.getCrudModel().getMetricas().add(metrica);
		}

		return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();
		this.getCrudModel().setDescricao(new String());

		return null;
	}

	@Override
	protected String find() {
		if (admLogado) {
			super.find();
		} else {
			this.addErrorMessage("Operação não permitida!");
		}
		return null;
	}

	public String addPermissao() {

		Permissao permissao = new Permissao();

		permissao.setEmpresa(getCrudModel());
		permissao.setMenu(new Menu());
		permissao.setFlagInserir(Boolean.TRUE);
		permissao.setFlagAlterar(Boolean.TRUE);
		permissao.setFlagExcluir(Boolean.FALSE);

		if (TSUtil.isEmpty(getCrudModel().getPermissoes())) {
			getCrudModel().setPermissoes(new ArrayList<Permissao>());
		}

		if (!this.getCrudModel().getPermissoes().contains(permissao)) {

			this.getCrudModel().getPermissoes().add(permissao);

		} else {

			CardapioUtil.addErrorMessage("Essa permissão já foi adicionada");
		}

		return null;
	}

	public String delPermissao() {
		getCrudModel().getPermissoes().remove(this.permissaoSelecionada);
		return null;
	}

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (TSUtil.isEmpty(getCrudModel().getLatitude()) || getCrudModel().getLatitude().equals(0D)) {
			addErrorMessage("Selecione uma posição no mapa.");
			validado = false;
		}

		if (!TSUtil.isEmailValid(this.getCrudModel().getEmail())) {
			this.addErrorMessage("E-mail inválido!");
			validado = false;
		}

		return validado;
	}

	public List<SelectItem> getComboMenus() {
		return comboMenus;
	}

	public void setComboMenus(List<SelectItem> comboMenus) {
		this.comboMenus = comboMenus;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public boolean isAdmLogado() {
		return admLogado;
	}

	public void setAdmLogado(boolean admLogado) {
		this.admLogado = admLogado;
	}
}
