package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;

import br.com.login.cardapio.ws.dao.TipoAgendaDAO;
import br.com.login.cardapio.ws.dao.TokenDAO;
import br.com.login.cardapio.ws.model.Token;

@Path("/tokens")
public class TokenService extends RestService<Token> {

	@Override
	public void initDAO() {
		this.restDAO = new TokenDAO();
	}
}
