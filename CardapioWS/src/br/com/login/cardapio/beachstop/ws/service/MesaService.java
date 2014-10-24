package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.login.cardapio.beachstop.ws.dao.MesaDAO;
@Path("/mesas")
public class MesaService extends RestService<Mesa> {

	@Override
	public void initDAO() {
		this.restDAO = new MesaDAO();
	}

}
