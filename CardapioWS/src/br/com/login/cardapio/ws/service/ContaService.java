package br.com.login.cardapio.ws.service;

import java.sql.Timestamp;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.ws.dao.ContaDAO;
import br.com.login.cardapio.ws.dao.EmpresaDAO;
import br.com.login.cardapio.ws.dao.LogDAO;
import br.com.login.cardapio.ws.exception.ApplicationException;
import br.com.login.cardapio.ws.model.Conta;
import br.com.login.cardapio.ws.model.Empresa;
import br.com.login.cardapio.ws.model.Pedido;
import br.com.login.cardapio.ws.model.PedidoSubItem;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/contas")
public class ContaService extends RestService<Conta> {

	private static final String MESA_OCUPADA = "Mesa ocupada! Tente a mesa ao lado!";

	@Override
	public void initDAO() {
		this.restDAO = new ContaDAO();
	}

	@Override
	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public Conta insert(@Form Conta form) throws ApplicationException {

		form.setFlagAberto(true);
		form.setEmpresa(new EmpresaDAO().get(form.getEmpresa().getKeyMobile()));
		form.setHorarioChegada(new Timestamp(System.currentTimeMillis()));
		form.setValor(new Double(0));
		form.setValorPago(new Double(0));

		return new Conta(super.insert(form).getId().toString());
	}

	@PUT
	@Path("/fechar/{id}")
	public void updateFecharConta(@PathParam("id") Long id) throws ApplicationException {

		try {

			Conta conta = new Conta();

			conta.setId(id);

			conta.setFlagAberto(Boolean.FALSE);

			new ContaDAO().updateFecharConta(conta);

			// TODO FAZER O ALERTA DEPOIS QUE FECHAR CONTA

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	@GET
	@Path("/historico/mesa/{n_mesa}/empresa/{keymobile}/log/{log}")
	@Produces("application/json; charset=UTF-8")
	public Conta historico(@PathParam("n_mesa") Long mesa, @PathParam("keymobile") String keymobile, @PathParam("log") String log) throws ApplicationException {

		Boolean booLog = Boolean.valueOf(log);

		Empresa empresa = new EmpresaDAO().get(keymobile);

		Conta conta = new ContaDAO().getByMesaEmpresa(mesa, empresa.getId());

		if (booLog) {
			LogDAO logDAO = new LogDAO();

			for (PedidoSubItem subItem : conta.getPedidoSubItem()) {
				subItem.setLogs(logDAO.getAll(subItem));
			}

		}

		return conta;
	}

	@Override
	protected void validate(Conta object) throws ApplicationException {

//		if (new ContaDAO().isBusyByTable(object)) {
//			throw new ApplicationException(MESA_OCUPADA, Response.SC_PRECONDITION_FAILED);
//		}
	}
}
