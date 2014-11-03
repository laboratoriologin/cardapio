package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.TipoQuantidade;
import br.com.login.cardapio.ws.dao.TipoQuantidadeDAO;
@Path("/tipos_quantidades")
public class TipoQuantidadeService extends RestService<TipoQuantidade> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoQuantidadeDAO();
	}

}
