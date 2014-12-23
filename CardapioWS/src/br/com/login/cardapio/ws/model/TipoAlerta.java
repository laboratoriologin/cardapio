package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name = "tipousuario")
public final class TipoAlerta extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoAlerta() {
	}

	public TipoAlerta(String id) {
		this.id = Long.valueOf(id);
	}
}