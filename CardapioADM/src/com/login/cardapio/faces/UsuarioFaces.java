package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.login.cardapio.model.GrupoUsuario;
import com.login.cardapio.model.Usuario;

@ViewScoped
@ManagedBean(name = "usuarioFaces")
public class UsuarioFaces extends CrudFaces<Usuario> {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> comboGrupoUsuario;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboGrupoUsuario = super.initCombo(new GrupoUsuario().findByModel("descricao"), "id", "descricao");
		setFieldOrdem("descricao");
	}

	@Override
	public String limparPesquisa() {
		String retorno = super.limparPesquisa();
		return retorno;
	}

	public List<SelectItem> getComboGrupoUsuario() {
		return comboGrupoUsuario;
	}

	public void setComboGrupoUsuario(List<SelectItem> comboGrupoUsuario) {
		this.comboGrupoUsuario = comboGrupoUsuario;
	}
}
