package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Categoria;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EscolhaCategoriaFaces extends TSMainFaces {

	private List<Categoria> listCategoria;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		this.setListCategoria(new Categoria().findAll("id"));

	}

	@Override
	protected String update() throws TSApplicationException {

		for (Categoria categoria : listCategoria) {
			categoria.update();
		}

		this.clearFields();

		return null;

	}

	public List<Categoria> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}
}
