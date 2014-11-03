package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name = "metrica")
public class Metrica extends RestModel {

	@FormParam("empresa")
	private Empresa empresa;

	@FormParam("tipoalerta")
	private TipoAlerta tipoAlerta;

	@FormParam("valor")
	private String valor;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
 	}
	
	public Metrica() {
	
	}
	
	public Metrica(String id) {
		this.id = Long.valueOf(id);
	}

}
