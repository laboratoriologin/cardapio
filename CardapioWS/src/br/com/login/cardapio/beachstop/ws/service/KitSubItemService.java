package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.KitSubItem;
import br.com.login.cardapio.beachstop.ws.dao.KitSubItemDAO;
@Path("/kits_sub_itens")
public class KitSubItemService extends RestService<KitSubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new KitSubItemDAO();
	}

}
