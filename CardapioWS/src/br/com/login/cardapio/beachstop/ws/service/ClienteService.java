package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Cliente;
import br.com.login.cardapio.beachstop.ws.dao.ClienteDAO;
@Path("/clientes")
public class ClienteService extends RestService<Cliente> {

	@Override
	public void initDAO() {
		this.restDAO = new ClienteDAO();
	}

}
