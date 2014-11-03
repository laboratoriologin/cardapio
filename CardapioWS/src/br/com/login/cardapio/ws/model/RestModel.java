<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/model/RestModel.java
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

=======
package br.com.login.cardapio.ws.model;

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

>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/model/RestModel.java
}