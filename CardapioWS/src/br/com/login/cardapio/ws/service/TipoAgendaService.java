package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.TipoAgenda;
import br.com.login.cardapio.ws.dao.TipoAgendaDAO;
@Path("/tipos_agenda")
public class TipoAgendaService extends RestService<TipoAgenda> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoAgendaDAO();
	}

}
