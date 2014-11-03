<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/TipoPublicidadeService.java
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
=======
package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.TipoPublicidade;
import br.com.login.cardapio.ws.dao.TipoPublicidadeDAO;
@Path("/tipos_publicidades")
public class TipoPublicidadeService extends RestService<TipoPublicidade> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoPublicidadeDAO();
	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/TipoPublicidadeService.java
