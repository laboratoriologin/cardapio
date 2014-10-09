package br.com.login.cardapio.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.ws.dao.UsuariosMesasDAO;
import br.com.login.cardapio.ws.model.Usuario;
import br.com.login.cardapio.ws.model.UsuariosMesas;

@Path("/usuarios_mesas")
public class UsuariosMesasService extends RestService<UsuariosMesas> {

	@Override
	public void initDAO() {
		this.restDAO = new UsuariosMesasDAO();
	}

	@GET
	@Path("/byusuario/{id}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public List<UsuariosMesas> getByUsuario(@PathParam("id") Long id, @PathParam("fields") String fields) {

		Usuario usuario = new Usuario(id.toString());

		List<UsuariosMesas> list = new UsuariosMesasDAO().get(usuario);

		this.configureReturnObjects(list, fields);

		return list;

	}
}
