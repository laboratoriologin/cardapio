package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.dao.PagamentoDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Pagamento;

@Path("/pagamentos")
public class PagamentoService extends RestService<Pagamento> {

	@Override
	public void initDAO() {
		this.restDAO = new PagamentoDAO();
	}

	@GET
	@Path("pagamentobyconta/{conta_id}")
	@Produces("application/json; charset=UTF-8")
	public List<Pagamento> getPagamentoByConta(@PathParam("conta_id") String contaId) {
		Conta conta = new Conta(contaId);
		return new PagamentoDAO().getAllByConta(conta);
	}		
}
