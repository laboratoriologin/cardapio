package br.com.login.cardapio.beachstop.ws.model;

import java.io.Serializable;
import javax.ws.rs.FormParam;
@SuppressWarnings("serial")
public class RestModel implements Serializable {

	@FormParam(value = "id")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

}