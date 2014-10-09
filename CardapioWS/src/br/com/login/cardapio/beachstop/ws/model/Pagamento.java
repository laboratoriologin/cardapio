package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name ="pagamento")
public final class Pagamento extends RestModel {

	@FormParam("conta")
	private Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta=conta;
	}

	@FormParam("data")
	private Date data;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data=data;
	}

	@FormParam("tipopagamento")
	private TipoPagamento tipoPagamento;

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento=tipoPagamento;
	}

	public Pagamento(){}

	public Pagamento(String id){
		this.id = Long.valueOf(id);
	}
}