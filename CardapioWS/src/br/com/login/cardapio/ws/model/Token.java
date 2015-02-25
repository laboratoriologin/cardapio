package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="token")
public final class Token extends RestModel {

	@FormParam("token")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token=token;
	}

	public Token(){}

	public Token(String id){
		this.id = Long.valueOf(id);
	}
}