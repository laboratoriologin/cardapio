package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.GrupoUsuario;
import br.com.login.cardapio.beachstop.ws.dao.GrupoUsuarioDAO;
@Path("/grupos_usuarios")
public class GrupoUsuarioService extends RestService<GrupoUsuario> {

	@Override
	public void initDAO() {
		this.restDAO = new GrupoUsuarioDAO();
	}

}
