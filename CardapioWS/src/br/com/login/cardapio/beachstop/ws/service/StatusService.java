package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.dao.StatusDAO;
@Path("/status")
public class StatusService extends RestService<Status> {

	@Override
	public void initDAO() {
		this.restDAO = new StatusDAO();
	}

}
