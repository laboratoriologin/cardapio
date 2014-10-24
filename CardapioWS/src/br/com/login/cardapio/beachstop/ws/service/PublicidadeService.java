package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Publicidade;
import br.com.login.cardapio.beachstop.ws.dao.PublicidadeDAO;
@Path("/publicidades")
public class PublicidadeService extends RestService<Publicidade> {

	@Override
	public void initDAO() {
		this.restDAO = new PublicidadeDAO();
	}

}
