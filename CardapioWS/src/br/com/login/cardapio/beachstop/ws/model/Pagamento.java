package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name = "pagamento")
public final class Pagamento extends RestModel {

	@Form(prefix = "conta")
	private Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@FormParam("data")
	private Date data;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@FormParam("strHorairo")
	private String strHorairo;

	public String getStrHorairo() {
		return this.strHorairo;
	}

	public void setStrHorairo(String strHorairo) {
		this.strHorairo = strHorairo;
	}

	@Form(prefix = "tipopagamento")
	private TipoPagamento tipoPagamento;

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	@FormParam("valor")
	private Double valor;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Pagamento() {
	}

	public Pagamento(String id) {
		this.id = Long.valueOf(id);
	}
}