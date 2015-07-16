package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.Setor;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.dao.MesaDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoSubItemDAO;

@Path("/mesas")
public class MesaService extends RestService<Mesa> {

	@Override
	public void initDAO() {
		this.restDAO = new MesaDAO();
	}

	@GET
	@Path("setor/{id}")
	@Produces("application/json; charset=UTF-8")
	public List<Mesa> getMesasOcupadasBySetor(@PathParam("id") String id) {
		Setor setor;
		if (!"0".equals(id)) {
			setor = new Setor(id);
		} else {
			setor = null;
		}
		return new MesaDAO().getAll(setor);
	}
}
