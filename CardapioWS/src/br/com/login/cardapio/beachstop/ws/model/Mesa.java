package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="mesa")
public final class Mesa extends RestModel {

	@FormParam("numero")
	private Integer numero;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero=numero;
	}

	@FormParam("setor")
	private Setor setor;

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor=setor;
	}

	public Mesa(){}

	public Mesa(String id){
		this.id = Long.valueOf(id);
	}
}