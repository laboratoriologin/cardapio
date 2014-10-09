package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.TipoAgenda;
import br.com.login.cardapio.beachstop.ws.dao.TipoAgendaDAO;
@Path("/tipos_agendas")
public class TipoAgendaService extends RestService<TipoAgenda> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoAgendaDAO();
	}

}
