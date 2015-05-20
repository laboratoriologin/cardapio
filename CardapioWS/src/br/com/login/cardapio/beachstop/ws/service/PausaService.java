package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;

import br.com.login.cardapio.beachstop.ws.dao.PausaDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.Pausa;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/pausas")
public class PausaService extends RestService<Pausa> {

	@Override
	public void initDAO() {
		this.restDAO = new PausaDAO();
	}

	@GET
	@Path("getempausa/")
	@Produces("application/json; charset=UTF-8")
	public List<Pausa> getEmPausa() {
		return new PausaDAO().getEmPausa();
	}
	
	@GET
	@Path("getempausa/{usuario_id}")
	@Produces("application/json; charset=UTF-8")
	public Pausa getEmPausa(@PathParam("usuario_id") String usuarioId) {
		return new PausaDAO().get(new Usuario(usuarioId));
	}

	@POST
	@Path("abrirpausa/{usuario_id}")
	@Produces("application/json; charset=UTF-8")
	public Pausa abrirPausa(@PathParam("usuario_id") String usuarioId) throws ApplicationException {
		try {

			Usuario usuario = new Usuario(usuarioId);
			Pausa pausa = new Pausa();
			pausa.setUsuario(usuario);

			PausaDAO pausaDAO = new PausaDAO();	
			
			if(pausaDAO.usuarioPaused(usuario)){
				throw new ApplicationException("Usuario já em pausa!", Response.SC_PRECONDITION_FAILED);				
			}else{
				return new PausaDAO().insert(pausa);
			}
			
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("fecharpausa/{usuario_id}")
	@Produces("application/json; charset=UTF-8")
	public Pausa fecharPausa(@PathParam("usuario_id") String usuario_id) throws ApplicationException {
		try {
			Pausa pausa = new PausaDAO().get(new Usuario(usuario_id));
			return new PausaDAO().update(pausa);
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	@PUT
	@Path("fecharpausa/pausa/{pausa_id}")
	@Produces("application/json; charset=UTF-8")
	public Pausa fecharPausaADM(@PathParam("pausa_id") String pausaId) throws ApplicationException {
		try {
			Pausa pausa = new PausaDAO().get(new Pausa(pausaId));
			return new PausaDAO().update(pausa);
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}
}