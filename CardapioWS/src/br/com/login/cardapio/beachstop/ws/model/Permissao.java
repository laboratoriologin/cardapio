package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="permissao")
public final class Permissao extends RestModel {

	@FormParam("flagalterar")
	private Boolean flagAlterar;

	public Boolean getFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(Boolean flagAlterar) {
		this.flagAlterar=flagAlterar;
	}

	@FormParam("flagexcluir")
	private Boolean flagExcluir;

	public Boolean getFlagExcluir() {
		return flagExcluir;
	}

	public void setFlagExcluir(Boolean flagExcluir) {
		this.flagExcluir=flagExcluir;
	}

	@FormParam("flaginserir")
	private Boolean flagInserir;

	public Boolean getFlagInserir() {
		return flagInserir;
	}

	public void setFlagInserir(Boolean flagInserir) {
		this.flagInserir=flagInserir;
	}

	@FormParam("grupousuario")
	private GrupoUsuario grupoUsuario;

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario=grupoUsuario;
	}

	@FormParam("menu")
	private Menu menu;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu=menu;
	}

	public Permissao(){}

	public Permissao(String id){
		this.id = Long.valueOf(id);
	}
}