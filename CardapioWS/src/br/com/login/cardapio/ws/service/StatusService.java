package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.Status;
import br.com.login.cardapio.ws.dao.StatusDAO;
@Path("/status")
public class StatusService extends RestService<Status> {

	@Override
	public void initDAO() {
		this.restDAO = new StatusDAO();
	}

}
