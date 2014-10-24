package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.login.cardapio.beachstop.ws.dao.AreasDAO;
@Path("/areas")
public class AreasService extends RestService<Area> {

	@Override
	public void initDAO() {
		this.restDAO = new AreasDAO();
	}

}
