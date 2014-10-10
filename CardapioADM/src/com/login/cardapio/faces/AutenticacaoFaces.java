package com.login.cardapio.faces;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

import com.login.cardapio.model.Menu;
import com.login.cardapio.model.Permissao;
import com.login.cardapio.model.Usuario;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.UsuarioUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces {

	private String nomeTela;
	private String tela;
	private Usuario usuario;
	private List<Menu> menus;
	private List<Permissao> permissoes;
	private Permissao PermissaoSelecionada;
	private Integer tabAtiva;

	public AutenticacaoFaces() throws TSApplicationException {

		clearFields();

		setTabAtiva(new Integer(0));

		setNomeTela("Área de Trabalho");

	}

	protected void clearFields() {

		this.usuario = new Usuario();

		this.menus = Collections.emptyList();

		this.PermissaoSelecionada = new Permissao();

	}

	public String redirecionar() {

		if (!TSUtil.isEmpty(this.PermissaoSelecionada.getMenu().getManagedBeanReset())) {
			TSFacesUtil.removeManagedBeanInSession(this.PermissaoSelecionada.getMenu().getManagedBeanReset());
		}

		setTela(this.PermissaoSelecionada.getMenu().getUrl());
		setNomeTela("Área de Trabalho > " + PermissaoSelecionada.getMenu().getMenuPai().getDescricao() + " > " + PermissaoSelecionada.getMenu().getDescricao());
		setTabAtiva(Integer.valueOf(this.menus.indexOf(this.PermissaoSelecionada.getMenu().getMenuPai())));

		return SUCESSO;
	}

	private void carregarMenu() {

		menus = new Menu().pesquisarCabecalhos(UsuarioUtil.obterUsuarioConectado().getGrupoUsuario().getId());

		Permissao permissao = new Permissao();
		permissao.setGrupoUsuario(UsuarioUtil.obterUsuarioConectado().getGrupoUsuario());
		permissoes = permissao.pesquisarPermissoes();

	}

	public String login() {

		usuario = UsuarioUtil.usuarioAutenticado(usuario);

		if (TSUtil.isEmpty(usuario)) {
			clearFields();
			CardapioUtil.addWarnMessage("Login/Senha sem credencial para acesso.");
			return null;
		}

		TSFacesUtil.addObjectInSession(Constantes.USUARIO_CONECTADO, usuario);
		carregarMenu();

		return SUCESSO;
	}

	public String logout() {
		TSFacesUtil.getRequest().getSession().invalidate();
		return "sair";
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public Permissao getPermissaoSelecionada() {
		return PermissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		PermissaoSelecionada = permissaoSelecionada;
	}
}
