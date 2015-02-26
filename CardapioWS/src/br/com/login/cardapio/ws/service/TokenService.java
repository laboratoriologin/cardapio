package br.com.login.cardapio.ws.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.ws.dao.TokenDAO;
import br.com.login.cardapio.ws.exception.ApplicationException;
import br.com.login.cardapio.ws.model.Token;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/tokens")
public class TokenService extends RestService<Token> {

	@Override
	public void initDAO() {
		this.restDAO = new TokenDAO();
	}
	
	@POST
	@Path("/iphone")
	@Produces("application/json; charset=UTF-8")
	public Token insertIPhone(@Form Token form) throws ApplicationException {
		try {

			this.validate(form);

			return new TokenDAO().insertIPhone(form);

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}
	
}
