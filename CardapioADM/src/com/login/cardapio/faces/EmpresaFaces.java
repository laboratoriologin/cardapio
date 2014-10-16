package com.login.cardapio.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Empresa;

@ViewScoped
@ManagedBean(name = "empresaFaces")
public class EmpresaFaces extends TSMainFaces {

	private static final long serialVersionUID = 1L;
	private Empresa empresa;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		this.setEmpresa(new Empresa().getEmpresa());

	}

	@Override
	protected String update() throws TSApplicationException {

		this.empresa.update();

		return null;

	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
