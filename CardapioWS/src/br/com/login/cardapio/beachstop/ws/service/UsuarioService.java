package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioDAO;
@Path("/usuarios")
public class UsuarioService extends RestService<Usuario> {

	@Override
	public void initDAO() {
		this.restDAO = new UsuarioDAO();
	}

}
