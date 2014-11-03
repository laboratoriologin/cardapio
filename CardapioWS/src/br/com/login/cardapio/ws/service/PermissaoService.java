<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/PermissaoService.java
package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Permissao;
import br.com.login.cardapio.beachstop.ws.dao.PermissaoDAO;
@Path("/permissoes")
public class PermissaoService extends RestService<Permissao> {

	@Override
	public void initDAO() {
		this.restDAO = new PermissaoDAO();
	}

}
=======
package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.Permissao;
import br.com.login.cardapio.ws.dao.PermissaoDAO;
@Path("/permissoes")
public class PermissaoService extends RestService<Permissao> {

	@Override
	public void initDAO() {
		this.restDAO = new PermissaoDAO();
	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/PermissaoService.java
