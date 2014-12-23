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