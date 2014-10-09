package br.com.login.cardapio.ws.model;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pedidos_alertas")
public class PedidosAlertas implements Serializable {

	@FormParam("alertas")
	private List<Alerta> alertas;
	@FormParam("contas")
	private List<Conta> contas;

	public List<Alerta> getAlertas() {
		return alertas;
	}

	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

}
