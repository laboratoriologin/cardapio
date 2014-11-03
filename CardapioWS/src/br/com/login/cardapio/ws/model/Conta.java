package br.com.login.cardapio.ws.model;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

@SuppressWarnings("serial")
@XmlRootElement(name = "conta")
public final class Conta extends RestModel {

	@FormParam("flagaberto")
	private Boolean flagAberto;

	public Boolean getFlagAberto() {
		return flagAberto;
	}

	public void setFlagAberto(Boolean flagAberto) {
		this.flagAberto = flagAberto;
	}

	@FormParam("horariochegada")
	private Date horarioChegada;

	public Date getHorarioChegada() {
		return horarioChegada;
	}

	public void setHorarioChegada(Date horarioChegada) {
		this.horarioChegada = horarioChegada;
	}

	@FormParam("horariosaida")
	private Date horarioSaida;

	public Date getHorarioSaida() {
		return horarioSaida;
	}

	public void setHorarioSaida(Date horarioSaida) {
		this.horarioSaida = horarioSaida;
	}

	@Form(prefix = "empresa")
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@FormParam("mesa")
	private Integer mesa;

	public Integer getMesa() {
		return mesa;
	}

	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	@FormParam("valor")
	private Double valor;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@FormParam("valorpago")
	private Double valorPago;

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	private List<PedidoSubItem> pedidoSubItem;

	public List<PedidoSubItem> getPedidoSubItem() {
		return pedidoSubItem;
	}

	public void setPedidoSubItem(List<PedidoSubItem> item) {
		this.pedidoSubItem = item;
	}

	private List<Pedido> pedido;

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> item) {
		this.pedido = item;
	}

	public Conta() {
	}

	public Conta(String id) {
		this.id = Long.valueOf(id);
	}
}