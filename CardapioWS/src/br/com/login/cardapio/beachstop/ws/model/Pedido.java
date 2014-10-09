package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="pedido")
public final class Pedido extends RestModel {

	@FormParam("conta")
	private Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta=conta;
	}

	@FormParam("observacao")
	private String observacao;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao=observacao;
	}

	public Pedido(){}

	public Pedido(String id){
		this.id = Long.valueOf(id);
	}
}