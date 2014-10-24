package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
@Path("/acoes_contas")
public class AcaoContaService extends RestService<AcaoConta> {

	@Override
	public void initDAO() {
		this.restDAO = new AcaoContaDAO();
	}

}
