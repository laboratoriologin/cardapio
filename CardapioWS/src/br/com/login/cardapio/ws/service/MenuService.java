package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.Menu;
import br.com.login.cardapio.ws.dao.MenuDAO;
@Path("/menus")
public class MenuService extends RestService<Menu> {

	@Override
	public void initDAO() {
		this.restDAO = new MenuDAO();
	}

}
