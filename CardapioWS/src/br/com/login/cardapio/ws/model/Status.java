<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/model/Status.java
package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="status")
public final class Status extends RestModel {
	
	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	public Status(){}

	public Status(String id){
		this.id = Long.valueOf(id);
	}
	
	public Status(Long id){
		this.id = id;
	}
=======
package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="status")
public final class Status extends RestModel {
	
	public static final Long PENDENTE_APROVACAO = 1L;
	public static final Long PENDENTE_ENTREGA = 2L;
	public static final Long ENTREGUE = 3L;
	public static final Long CANCELADO = 4L;

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	public Status(){}

	public Status(String id){
		this.id = Long.valueOf(id);
	}
	
	public Status(Long id){
		this.id = id;
	}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/model/Status.java
}