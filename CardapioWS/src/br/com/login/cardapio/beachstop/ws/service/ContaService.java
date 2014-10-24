package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
@Path("/contas")
public class ContaService extends RestService<Conta> {

	@Override
	public void initDAO() {
		this.restDAO = new ContaDAO();
	}

}
