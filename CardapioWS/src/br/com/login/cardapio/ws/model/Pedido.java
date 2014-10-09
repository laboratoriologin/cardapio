package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@XmlRootElement(name = "pedido")
public final class Pedido extends RestModel {

	@FormParam("horario")
	private String horario;

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	@Form(prefix = "conta")
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

	@Form(prefix = "listPedidoSubItem")
	private List<PedidoSubItem> listPedidoSubItem;

	public List<PedidoSubItem> getListPedidoSubItem() {
		return listPedidoSubItem;
	}

	public void setListPedidoSubItem(List<PedidoSubItem> listPedidoSubItem) {
		this.listPedidoSubItem = listPedidoSubItem;
	}

	@FormParam("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@FormParam("cormetrica")
	private String corMetrica;

	public String getCorMetrica() {
		return corMetrica;
	}

	public void setCorMetrica(String corMetrica) {
		this.corMetrica = corMetrica;
	}

	public Pedido() {
	}

	public Pedido(String id) {
		this.id = Long.valueOf(id);
	}
}