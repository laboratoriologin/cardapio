<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/SubItemService.java
package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.SubItem;
import br.com.login.cardapio.beachstop.ws.dao.SubItemDAO;
@Path("/sub_itens")
public class SubItemService extends RestService<SubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new SubItemDAO();
	}

}
=======
package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.SubItem;
import br.com.login.cardapio.ws.dao.SubItemDAO;
@Path("/sub_itens")
public class SubItemService extends RestService<SubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new SubItemDAO();
	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/SubItemService.java
