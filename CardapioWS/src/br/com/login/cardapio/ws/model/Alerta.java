package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ricardo
 * 
 */
@SuppressWarnings("serial")
@XmlRootElement(name = "alerta")
public class Alerta extends RestModel {

	@FormParam("flagresolvido")
	private Boolean flagResolvido;

	@FormParam("conta")
	private Conta conta;

	@FormParam("tipoalerta")
	private TipoAlerta tipoAlerta;

	@FormParam("metrica")
	private Metrica metrica;

	@FormParam("horario")
	private String horario;

	@FormParam("cormetrica")
	private String corMetrica;

	/**
	 * @return the flagresolvido
	 */
	public Boolean getFlagResolvido() {
		return flagResolvido;
	}

	/**
	 * @param flagresolvido
	 *            the flagresolvido to set
	 */
	public void setFlagResolvido(Boolean flagResolvido) {
		this.flagResolvido = flagResolvido;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Metrica getMetrica() {
		return metrica;
	}

	public void setMetrica(Metrica metrica) {
		this.metrica = metrica;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getCorMetrica() {
		return corMetrica;
	}

	public void setCorMetrica(String corMetrica) {
		this.corMetrica = corMetrica;
	}

	public Alerta() {
	}

	public Alerta(String id) {
		this.id = Long.valueOf(id);
	}

}
