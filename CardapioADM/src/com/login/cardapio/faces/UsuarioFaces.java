package com.login.cardapio.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.login.cardapio.model.GrupoUsuario;
import com.login.cardapio.model.Setor;
import com.login.cardapio.model.Usuario;
import com.login.cardapio.model.UsuarioSetor;

@ViewScoped
@ManagedBean(name = "usuarioFaces")
public class UsuarioFaces extends CrudFaces<Usuario> {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> comboGrupoUsuario;
	private List<Setor> listSetor;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.listSetor = new Setor().findAll("id");
		this.comboGrupoUsuario = super.initCombo(new GrupoUsuario().findByModel("descricao"), "id", "descricao");
		getCrudModel().setListUsuarioSetor(new ArrayList<UsuarioSetor>());
		getCrudModel().setGrupoUsuario(new GrupoUsuario());
		setFieldOrdem("nome");
	}

	@Override
	protected String detail() {
		super.detail();

		int index;
		for (UsuarioSetor usuarioSetor : getCrudModel().getListUsuarioSetor()) {

			index = listSetor.indexOf(usuarioSetor.getSetor());
			listSetor.get(index).setSelecionado(true);

		}

		return null;
	}

	@Override
	protected void prePersist() {

		UsuarioSetor usuarioSetor;
		for (Setor setor : listSetor) {

			if (setor.getSelecionado()) {
				usuarioSetor = new UsuarioSetor();

				usuarioSetor.setUsuario(getCrudModel());
				usuarioSetor.setSetor(setor);

				getCrudModel().getListUsuarioSetor().add(usuarioSetor);

			}

		}

		super.prePersist();
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

	public List<Setor> getListSetor() {
		return listSetor;
	}

	public void setListSetor(List<Setor> listSetor) {
		this.listSetor = listSetor;
	}

}
