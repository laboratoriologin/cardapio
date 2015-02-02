package br.com.login.cardapio.beachstop.ws.model;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

@SuppressWarnings("serial")
@XmlRootElement(name = "pedido")
public final class Pedido extends RestModel {

	@FormParam("conta")
	private Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@FormParam("observacao")
	private String observacao;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@FormParam("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Form(prefix = "subItens")
	private List<PedidoSubItem> subItens;

	public List<PedidoSubItem> getSubItens() {
		return subItens;
	}

	public void setSubItens(List<PedidoSubItem> subItens) {
		this.subItens = subItens;
	}
	
	@FormParam("numero")
	private Long numero;
	
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	@FormParam("acaoContaId")
	private Long acaoContaId;
	
	public Long getAcaoContaId() {
		return acaoContaId;
	}

	public void setAcaoContaId(Long acaoContaId) {
		this.acaoContaId = acaoContaId;
	}
	
	public Pedido() {
	}
	
	public Pedido(String id) {
		this.id = Long.valueOf(id);
	}
}