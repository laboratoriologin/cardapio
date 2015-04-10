package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Setor;
import br.com.login.cardapio.beachstop.ws.dao.SetorDAO;
@Path("/setores")
public class SetorService extends RestService<Setor> {

	@Override
	public void initDAO() {
		this.restDAO = new SetorDAO();
	}

}
