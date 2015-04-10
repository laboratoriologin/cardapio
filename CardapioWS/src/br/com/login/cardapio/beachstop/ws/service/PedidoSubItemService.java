package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.dao.PedidoSubItemDAO;
@Path("/pedidos_sub_itens")
public class PedidoSubItemService extends RestService<PedidoSubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new PedidoSubItemDAO();
	}

}
