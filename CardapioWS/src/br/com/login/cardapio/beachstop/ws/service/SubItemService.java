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
