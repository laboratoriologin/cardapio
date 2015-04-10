package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.TipoPublicidade;
import br.com.login.cardapio.beachstop.ws.dao.TipoPublicidadeDAO;
@Path("/tipos_publicidades")
public class TipoPublicidadeService extends RestService<TipoPublicidade> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoPublicidadeDAO();
	}

}
