package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.AgendaPublicidade;
import br.com.login.cardapio.beachstop.ws.dao.AgendaPublicidadeDAO;
@Path("/agendas_publicidades")
public class AgendaPublicidadeService extends RestService<AgendaPublicidade> {

	@Override
	public void initDAO() {
		this.restDAO = new AgendaPublicidadeDAO();
	}

}
