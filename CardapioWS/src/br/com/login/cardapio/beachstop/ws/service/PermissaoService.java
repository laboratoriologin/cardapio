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
