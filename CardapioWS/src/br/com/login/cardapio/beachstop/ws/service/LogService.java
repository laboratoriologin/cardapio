package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Log;
import br.com.login.cardapio.beachstop.ws.dao.LogDAO;
@Path("/logs")
public class LogService extends RestService<Log> {

	@Override
	public void initDAO() {
		this.restDAO = new LogDAO();
	}

}
