package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.PedidoSubItem;
import br.com.login.cardapio.ws.dao.PedidoSubItemDAO;
@Path("/itens_pedidos")
public class ItemPedidoService extends RestService<PedidoSubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new PedidoSubItemDAO();
	}

}
