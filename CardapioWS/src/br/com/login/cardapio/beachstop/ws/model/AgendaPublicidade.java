package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="agendapublicidade")
public final class AgendaPublicidade extends RestModel {

	@FormParam("publicidade")
	private Publicidade publicidade;

	public Publicidade getPublicidade() {
		return publicidade;
	}

	public void setPublicidade(Publicidade publicidade) {
		this.publicidade=publicidade;
	}

	@FormParam("tipoagenda")
	private TipoAgenda tipoAgenda;

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda=tipoAgenda;
	}

	@FormParam("valor")
	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor=valor;
	}

	public AgendaPublicidade(){}

	public AgendaPublicidade(String id){
		this.id = Long.valueOf(id);
	}
}