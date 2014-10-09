package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.TipoUsuario;
import br.com.login.cardapio.beachstop.ws.dao.TipoUsuarioDAO;
@Path("/tipos_usuarios")
public class TipoUsuarioService extends RestService<TipoUsuario> {

	@Override
	public void initDAO() {
		this.restDAO = new TipoUsuarioDAO();
	}

}
