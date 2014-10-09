package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.UsuarioSetor;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioSetorDAO;
@Path("/usuarios_setores")
public class UsuarioSetorService extends RestService<UsuarioSetor> {

	@Override
	public void initDAO() {
		this.restDAO = new UsuarioSetorDAO();
	}

}
