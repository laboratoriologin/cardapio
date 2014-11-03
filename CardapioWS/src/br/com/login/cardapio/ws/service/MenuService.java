<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/MenuService.java
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
=======
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
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/MenuService.java
