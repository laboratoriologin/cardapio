<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/model/TipoAgenda.java
package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="tipoagenda")
public final class TipoAgenda extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	public TipoAgenda(){}

	public TipoAgenda(String id){
		this.id = Long.valueOf(id);
	}
=======
package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="tipoagenda")
public final class TipoAgenda extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	public TipoAgenda(){}

	public TipoAgenda(String id){
		this.id = Long.valueOf(id);
	}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/model/TipoAgenda.java
}