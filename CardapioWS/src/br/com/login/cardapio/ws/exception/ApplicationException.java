<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/exception/ApplicationException.java
package br.com.login.cardapio.beachstop.ws.exception;

@SuppressWarnings("serial")
public class ApplicationException extends Exception  {

	private int responseStatus;

	public ApplicationException() {
	}

	public ApplicationException(Exception ex) {
		super(ex);
	}

	public ApplicationException(String message, int responseStatus) {
		super(message);
		this.responseStatus = responseStatus;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

=======
package br.com.login.cardapio.ws.exception;

@SuppressWarnings("serial")
public class ApplicationException extends Exception  {

	private int responseStatus;

	public ApplicationException() {
	}

	public ApplicationException(Exception ex) {
		super(ex);
	}

	public ApplicationException(String message, int responseStatus) {
		super(message);
		this.responseStatus = responseStatus;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/exception/ApplicationException.java
}