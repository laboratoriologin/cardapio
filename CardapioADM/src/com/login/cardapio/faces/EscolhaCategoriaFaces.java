package com.login.cardapio.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Empresa;
import com.login.cardapio.util.UsuarioUtil;
import com.login.cardapio.util.Utilitarios;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EscolhaCategoriaFaces extends TSMainFaces {

	private Empresa empresa;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		this.empresa = UsuarioUtil.obterUsuarioConectado().getEmpresa().getById();

	}

	@Override
	protected String update() throws TSApplicationException {

		empresa.setKeyCardapio(Utilitarios.gerarNomeArquivo());

		empresa.update();

		this.clearFields();

		return null;

	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
