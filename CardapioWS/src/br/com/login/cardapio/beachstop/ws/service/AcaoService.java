package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Acao;
import br.com.login.cardapio.beachstop.ws.dao.AcaoDAO;
@Path("/acoes")
public class AcaoService extends RestService<Acao> {

	@Override
	public void initDAO() {
		this.restDAO = new AcaoDAO();
	}

}
