package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="grupousuario")
public final class GrupoUsuario extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	public GrupoUsuario(){}

	public GrupoUsuario(String id){
		this.id = Long.valueOf(id);
	}
}