package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;

import br.com.login.cardapio.ws.dao.AgendaPublicidadeDAO;
import br.com.login.cardapio.ws.model.AgendaPublicidade;
@Path("/agendas_publicidade")
public class AgendaPublicidadeService extends RestService<AgendaPublicidade> {

	/**
	 * @author Ricardo
	 *
	 */
	
	@Override
	public void initDAO() {
		this.restDAO = new AgendaPublicidadeDAO();
	}
	
}
