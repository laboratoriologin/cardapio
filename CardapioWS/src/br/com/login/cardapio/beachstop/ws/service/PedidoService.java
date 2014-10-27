package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.LogDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoSubItemDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
@Path("/pedidos")
public class PedidoService extends RestService<Pedido> {

	@Override
	public void initDAO() {
		this.restDAO = new PedidoDAO();
	}
	
	@Override
	@GET
	@Path("/{id}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public Pedido get(@PathParam("id") Long id, @PathParam("fields") String fields) {

		Pedido pedido = restDAO.get(id);
		
		if (pedido != null) {
			
			LogDAO logDAO = new LogDAO();

			pedido.setSubItens(new PedidoSubItemDAO().getAll(pedido));

			for(PedidoSubItem subItem : pedido.getSubItens()) {
				
				subItem.setLogs(logDAO.getAll(subItem));
				
			}
			

			this.configureReturnObject(pedido, fields);

		}

		return pedido;

	}

	@Override
	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public Pedido insert(@Form Pedido form) throws ApplicationException {

		final boolean pedidoPeloGarcom = form.getUsuario() != null;

		Status status = new Status();

		if (pedidoPeloGarcom) {

			status.setId(Constantes.PEDIDO_PENDENTE_ENTREGA);

			
		} else {

			status.setId(Constantes.PEDIDO_PENDENTE_APROVACAO);

		}


		for (PedidoSubItem item : form.getSubItens()) {

			item.setStatus(status);

		}

		super.insert(form);

		if (form.getUsuario() == null) {

			form.setUsuario(new Usuario());

		}

		this.gerarLog(form, status);

		return new Pedido();

	}

	@PUT
	@Path("/{id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido update(@Form Pedido form, @PathParam("id") Long id) throws ApplicationException {

		PedidoDAO pedidoDAO = new PedidoDAO();

		Pedido order = pedidoDAO.get(id);
		// se ninguem fez nenhuma operacao antes, pode alterar o registro.
		// Apenas um controle de transação
		if (TSUtil.isEmpty(order.getUsuario().getId())) {

			form.setConta(order.getConta());

			super.update(form, id);

			this.gerarLog(form);

		}

		return new Pedido();

	}

	@PUT
	@Path("/aprovar/{id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido aprovar(@Form Pedido form, @PathParam("id") Long id) throws ApplicationException {

		try {

			PedidoDAO pedidoDAO = new PedidoDAO();

			Pedido pedido = pedidoDAO.get(id);

			// se ninguem fez nenhuma operacao antes, pode alterar o registro.
			// Apenas um controle de transação
			if (TSUtil.isEmpty(pedido.getUsuario().getId())) {

				form.setId(id);

				pedidoDAO.aprovar(form);

				form.setSubItens(new PedidoSubItemDAO().getAll(form));

				this.gerarLog(form, new Status(Constantes.PEDIDO_PENDENTE_ENTREGA));

			}

			return form;

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();

			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	@PUT
	@Path("/cancelar/{id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido cancelar(@Form Pedido pedido, @PathParam("id") Long id) throws ApplicationException {

		try {

			PedidoDAO pedidoDAO = new PedidoDAO();

			Pedido order = pedidoDAO.get(id);
			// se ninguem fez nenhuma operacao antes, pode alterar o registro.
			// Apenas um controle de transação
			if (TSUtil.isEmpty(order.getUsuario().getId())) {

				pedido.setId(id);

				pedido.setSubItens(new PedidoSubItemDAO().getAll(pedido));

				new PedidoDAO().cancelar(pedido);

				this.gerarLog(pedido, new Status(Constantes.PEDIDO_CANCELADO));

			}

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();

			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

		return pedido;

	}

	private void gerarLog(Pedido pedido, Status status) {

		try {

			for (PedidoSubItem subItem : pedido.getSubItens()) {

				subItem.setStatus(status);

			}

			new LogDAO().insert(pedido);

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	private void gerarLog(Pedido pedido) {

		try {

			new LogDAO().insert(pedido);

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

}
