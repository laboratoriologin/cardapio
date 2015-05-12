package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.model.GrupoUsuario;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.dao.GrupoUsuarioDAO;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioDAO;

@Path("/grupos_usuarios")
public class GrupoUsuarioService extends RestService<GrupoUsuario> {

	@Override
	public void initDAO() {
		this.restDAO = new GrupoUsuarioDAO();
	}

	@GET
	@Path("/getalln2")
	@Produces("application/json; charset=UTF-8")
	public List<GrupoUsuario> getAllN2() {
		return new GrupoUsuarioDAO().getAllN2();
	}
}
