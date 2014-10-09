package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Kit;
import br.com.login.cardapio.beachstop.ws.dao.KitDAO;
@Path("/kits")
public class KitService extends RestService<Kit> {

	@Override
	public void initDAO() {
		this.restDAO = new KitDAO();
	}

}
