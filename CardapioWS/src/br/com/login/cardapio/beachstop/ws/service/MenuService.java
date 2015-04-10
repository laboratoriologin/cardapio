package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Menu;
import br.com.login.cardapio.beachstop.ws.dao.MenuDAO;
@Path("/menus")
public class MenuService extends RestService<Menu> {

	@Override
	public void initDAO() {
		this.restDAO = new MenuDAO();
	}

}
