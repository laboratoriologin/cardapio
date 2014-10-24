package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.TipoPagamento;
import br.com.login.cardapio.beachstop.ws.dao.TipoPagamentoDAO;
@Path("/tipos_pagamentos")
public class TipoPagamentoService extends RestService<TipoPagamento> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoPagamentoDAO();
	}

}
