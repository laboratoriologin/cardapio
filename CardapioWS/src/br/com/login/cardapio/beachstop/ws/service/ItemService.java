package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Item;
import br.com.login.cardapio.beachstop.ws.dao.ItemDAO;
@Path("/itens")
public class ItemService extends RestService<Item> {

	@Override
	public void initDAO() {
		this.restDAO = new ItemDAO();
	}

}
