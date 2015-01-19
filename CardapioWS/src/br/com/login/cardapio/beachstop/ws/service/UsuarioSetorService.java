package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.model.UsuarioSetor;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioSetorDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/usuarios_setores")
public class UsuarioSetorService extends RestService<UsuarioSetor> {

	@Override
	public void initDAO() {
		this.restDAO = new UsuarioSetorDAO();
	}
	
	@GET
	@Path("/usuario/{id}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public List<UsuarioSetor> getByUsuario(@PathParam("id") Long id, @PathParam("fields") String fields) {

		List<UsuarioSetor> object = new UsuarioSetorDAO().getAllByUsuario(new Usuario(id.toString()));

		if (fields != null && !"".equals(fields)) {
			configureReturnObjects(object, fields);
		}
		return object;
	}
	
	@POST
	@Path("/save")
	@Produces("application/json; charset=UTF-8")
	public UsuarioSetor save(@Form UsuarioSetor form) throws ApplicationException {

		try {
			new UsuarioSetorDAO().save(form);
			return new UsuarioSetor();
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
