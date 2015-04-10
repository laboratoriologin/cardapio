package com.login.cardapio.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.login.cardapio.model.TipoPagamento;

@ViewScoped
@ManagedBean(name = "tipoPagamentoFaces")
public class TipoPagamentoFaces extends CrudFaces<TipoPagamento> {

	private static final long serialVersionUID = 1L;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}

	@Override
	public String limparPesquisa() {
		String retorno = super.limparPesquisa();
		return retorno;
	}
}
