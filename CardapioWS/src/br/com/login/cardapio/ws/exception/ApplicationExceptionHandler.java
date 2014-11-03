<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/exception/ApplicationExceptionHandler.java
package br.com.login.cardapio.beachstop.ws.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ExceptionMapper;
@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException>  {

	@Override
	public Response toResponse(ApplicationException exception) {
		return  Response.status(exception.getResponseStatus()).entity(exception.getMessage()).build();
	}

=======
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

>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/exception/ApplicationExceptionHandler.java
}