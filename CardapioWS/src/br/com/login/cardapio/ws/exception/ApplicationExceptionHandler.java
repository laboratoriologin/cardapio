package br.com.login.cardapio.ws.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ExceptionMapper;
@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException>  {

	@Override
	public Response toResponse(ApplicationException exception) {
		return  Response.status(exception.getResponseStatus()).entity(exception.getMessage()).build();
	}
}