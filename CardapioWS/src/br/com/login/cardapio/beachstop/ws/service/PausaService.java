package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Pausa;
import br.com.login.cardapio.beachstop.ws.dao.PausaDAO;
@Path("/pausas")
public class PausaService extends RestService<Pausa> {

	@Override
	public void initDAO() {
		this.restDAO = new PausaDAO();
	}

}
