package br.com.login.cardapio.beachstop.ws.service;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import com.sun.jmx.snmp.Timestamp;

import br.com.login.cardapio.beachstop.ws.model.Cliente;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;

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

		if (form.getCliente() == null) {

			form.setCliente(new Cliente("1"));
			form.setDataAbertura(new Date(System.currentTimeMillis()));
			form.setQtdPessoa(1);

		}

		return new Conta(super.insert(form).getId().toString());

	}

	@Override
	protected void validate(Conta object) throws ApplicationException {

		Conta conta = new ContaDAO().getByNumeroTipoConta(object.getNumero(), object.getTipoConta(), false);

		if (conta != null && conta.getDataFechamento() == null) {

			throw new ApplicationException(MESA_OCUPADA, Response.SC_PRECONDITION_FAILED);

		}

	}

}
