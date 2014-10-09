package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.Log;
import br.com.login.cardapio.ws.dao.LogDAO;
@Path("/logs")
public class LogService extends RestService<Log> {

	@Override
	public void initDAO() {
		this.restDAO = new LogDAO();
	}

}
