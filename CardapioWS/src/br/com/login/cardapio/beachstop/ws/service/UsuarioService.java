package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.dao.UsuarioDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.login.cardapio.beachstop.ws.util.EmailUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSCryptoUtil;

@Path("/usuarios")
public class UsuarioService extends RestService<Usuario> {

	@Override
	public void initDAO() {
		this.restDAO = new UsuarioDAO();
	}

	@GET
	@Path("/pesquisa/{filtro}")
	@Produces("application/json; charset=UTF-8")
	public List<Usuario> getUsuarioByFiltro(@PathParam(value = "filtro") String filtro) {
		return new UsuarioDAO().getAllByFiltro(filtro);
	}
	
	@GET
	@Path("/pesquisa")
	@Produces("application/json; charset=UTF-8")
	public List<Usuario> getUsuarioByFiltro() {
		return new UsuarioDAO().getAllByFiltro("");
	}

	@POST
	@Path("/loginAppGarcom")
	@Produces("application/json; charset=UTF-8")
	public Usuario login(@Form Usuario form) throws ApplicationException {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		form = usuarioDAO.loginAppGarcom(form);

		if (form == null) {

			throw new ApplicationException("Usuário inválido", Response.SC_BAD_REQUEST);

		}
		//
		// List<UsuariosMesas> list = new UsuariosMesasDAO().get(form);
		//
		// form.setListMesa(new ArrayList<String>());
		//
		// for (UsuariosMesas mesas : list) {
		//
		// form.getListMesa().add(mesas.getNumeroMesa().toString());
		//
		// }

		return form;

	}
	
	@PUT
	@Path("ativo/{id}")
	@Produces("application/json; charset=UTF-8")
	public Usuario updateAtivo(@Form Usuario form, @PathParam("id") Long id) throws ApplicationException {
		try {
			this.validate(form);
			form.setFlagAtivo(!form.getFlagAtivo());
			form.setId(id);
			return new UsuarioDAO().ativo(form);
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Path("/enviarEmail")
	@Produces("application/json; charset=UTF-8")
	public Usuario enviarEmail(@Form Usuario form) throws ApplicationException {

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		form = usuarioDAO.getByEmail(form);

		if (form == null) {
			throw new ApplicationException("Usuário não existe", Response.SC_BAD_REQUEST);
		}

		Random random = new Random();
		String novaSenha = "";

		for (int i = 0; i < 6; i++) {
			novaSenha += String.valueOf(random.nextInt(10));
		}

		try {
			form.setSenha(TSCryptoUtil.gerarHash(novaSenha, Constantes.CRIPTOGRAFIA_MD5));
			new UsuarioDAO().alterarSenha(form);
			EmailUtil.enviar(form.getEmail(), "Sua nova senha é: " + novaSenha);
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
		return form;
	}

	@Override
	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public Usuario insert(@Form Usuario form) throws ApplicationException {
		try {

			this.validate(form);
			
			form.setSenha(TSCryptoUtil.gerarHash(form.getSenha(), Constantes.CRIPTOGRAFIA_MD5));

			return restDAO.insert(form);

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

}
