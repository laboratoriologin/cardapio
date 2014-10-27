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
}