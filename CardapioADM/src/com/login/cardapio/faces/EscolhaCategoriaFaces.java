package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Categoria;
import com.login.cardapio.util.Utilitarios;

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
		
		try {
			Utilitarios.gerarNovoCodigoCardapio();
		} catch (TSApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage("Erro no sistema, entre em contato com o administrador, Erro: 0101!");
		}

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
