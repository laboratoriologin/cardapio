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

}