package br.com.login.cardapio.ws.model;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.resteasy.annotations.Form;

@SuppressWarnings("serial")
@XmlRootElement(name = "itempedido")
public final class PedidoSubItem extends RestModel {

	@XmlTransient
	private Long pedido;

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	@FormParam("subitem")
	private SubItem subitem;

	public SubItem getSubitem() {
		return subitem;
	}

	public void setSubitem(SubItem subitem) {
		this.subitem = subitem;
	}

	@FormParam("status")
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@FormParam("quantidade")
	private Integer quantidade;

	public Integer getQuantidade() {
		return quantidade;
	}

	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@FormParam("flagCancelado")
	private Boolean flagCancelado;

	public Boolean getFlagCancelado() {
		return flagCancelado;
	}

	public void setFlagCancelado(Boolean flagCancelado) {
		this.flagCancelado = flagCancelado;
	}

	@FormParam("horario")
	private String horario;

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	@Form(prefix = "logs")
	private List<Log> logs;

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public PedidoSubItem() {
	}

	public PedidoSubItem(String id) {
		this.id = Long.valueOf(id);
	}
}