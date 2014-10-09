package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.login.cardapio.model.Empresa;
import com.login.cardapio.model.Usuario;
import com.login.cardapio.util.UsuarioUtil;

@ViewScoped
@ManagedBean(name = "usuarioFaces")
public class UsuarioFaces extends CrudFaces<Usuario> {	
	
	private List<SelectItem> grupos;

	@PostConstruct
	protected void init() {
		this.clearFields();		
		this.grupos = super.initCombo(new Empresa().findAll("nome"), "id", "nome");
		setFieldOrdem("nome");
	}

	public String limpar() {
		super.limpar();		
		getCrudModel().setEmpresa(new Empresa());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();	
		getCrudPesquisaModel().setEmpresa(new Empresa());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return null;
	}
	
	protected void prePersist() {
		getCrudModel().setSenha(UsuarioUtil.getSenhaCriptografada(getCrudModel().getSenha()));
	}

	public List<SelectItem> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<SelectItem> grupos) {
		this.grupos = grupos;
	}	

}
