package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Pagamento;
import br.com.login.cardapio.beachstop.ws.dao.PagamentoDAO;
@Path("/pagamentos")
public class PagamentoService extends RestService<Pagamento> {

	@Override
	public void initDAO() {
		this.restDAO = new PagamentoDAO();
	}

}
