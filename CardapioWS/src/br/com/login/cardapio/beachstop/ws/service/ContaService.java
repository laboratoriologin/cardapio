package br.com.login.cardapio.beachstop.ws.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Cliente;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/contas")
public class ContaService extends RestService<Conta> {

	private static final String MESA_OCUPADA = "Mesa ocupada! Tente a mesa ao lado!";
	private static final String MESA_S_ASSOCIACAO = "false";

	@Override
	public void initDAO() {
		this.restDAO = new ContaDAO();
	}
	
	@GET
	@Path("/aberto/{id}")
	@Produces("application/json; charset=UTF-8")
	public Conta isAberta(@PathParam("id") Long id) {
		Conta conta = new ContaDAO().get(id);
		if(conta.getDataFechamento() == null)
			return new Conta("1");
		else
			return new Conta("0");
	}
	
	@GET
	@Path("/numero/{numero}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public Conta getByMesa(@PathParam("numero") Long numero, @PathParam("fields") String fields) {
		Conta conta = new ContaDAO().getByNumeroAnalitico(numero);
		if (fields != null && !"".equals(fields)) {
			this.configureReturnObject(conta, fields);
		}
		return conta;
	}
	
	@GET
	@Path("/contafechada/{numero}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public List<Conta> getContaFechadaByMesa(@PathParam("numero") Integer numero, @PathParam("fields") String fields) {
		Mesa mesa = new Mesa();
		mesa.setNumero(numero);
		List<Conta> conta = new ContaDAO().getContaFechadaByMesa(mesa);
		if (fields != null && !"".equals(fields)) {
			this.configureReturnObjects(conta, fields);
		}
		return conta;
	}

	@Override
	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public Conta insert(@Form Conta form) throws ApplicationException {
		if (form.getCliente() == null) {						
			form.setQtdPessoa(1);
		}		
		form.setDataAbertura(new Date(System.currentTimeMillis()));
		return new Conta(super.insert(form).getId().toString());
	}

	@PUT
	@Path("associar/{mesa}/cliente/{cliente}")
	@Produces("application/json; charset=UTF-8")
	public Conta associarCliente(@Form Conta form, @PathParam("mesa") Long mesa, @PathParam("cliente") Long clienteId) throws ApplicationException {
		try {			
			ContaDAO contaDAO = new ContaDAO();
			Conta conta  = contaDAO.getByMesa(mesa);
			conta.setCliente(new Cliente(clienteId.toString()));			
			return contaDAO.update(conta);			
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	protected void validate(Conta object) throws ApplicationException {
		Conta conta = new ContaDAO().getByNumeroTipoConta(object.getNumero(), object.getTipoConta(), false);
		
		if(conta != null){
			if(conta.getCliente().getId() != null){
				throw new ApplicationException(MESA_OCUPADA, Response.SC_PRECONDITION_FAILED);
			} else {
				throw new ApplicationException(MESA_S_ASSOCIACAO, Response.SC_PRECONDITION_FAILED);
			}
		}
	}
}
