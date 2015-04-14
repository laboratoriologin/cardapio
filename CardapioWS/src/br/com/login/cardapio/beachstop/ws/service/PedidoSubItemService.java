package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.dao.PedidoDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoSubItemDAO;

@Path("/pedidos_sub_itens")
public class PedidoSubItemService extends RestService<PedidoSubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new PedidoSubItemDAO();
	}

	@GET
	@Path("pedidosubitemgroupqtd/{conta_id}")
	@Produces("application/json; charset=UTF-8")
	public List<PedidoSubItem> get(@PathParam("conta_id") String contaId) {
		Conta conta = new Conta(contaId);
		return new PedidoSubItemDAO().getAllGroupQtd(conta);
	}

}
