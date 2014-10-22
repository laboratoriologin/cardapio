package com.login.beachstop.android.model;

import java.io.Serializable;
import java.util.List;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "CONTA")
public class Conta implements Serializable {

	@PrimaryKey
	@Column(name = "ID")
	private Long id;

	@Column(name = "MESA")
	private Long mesa;

	@Column(name = "VALOR")
	private String valor;

	@Column(name = "VALOR_PAGO")
	private String valorPago;

	@Column(name = "HORARIO_CHEGADA")
	private String horarioChegada;

	@Column(name = "IS_SHARE")
	private String isShare;

	@Transient
	private List<Pedido> listPedido;

	public Conta() {
		// TODO Auto-generated constructor stub
	}

	public Conta(Long mesa) {
		this.mesa = mesa;
	}

	public Long getMesa() {
		return mesa;
	}

	public void setMesa(Long mesa) {
		this.mesa = mesa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Pedido> getListPedido() {
		return listPedido;
	}

	public void setListPedido(List<Pedido> listPedido) {
		this.listPedido = listPedido;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValorPago() {
		return valorPago;
	}

	public void setValorPago(String valorPago) {
		this.valorPago = valorPago;
	}

	public String getHorarioChegada() {
		return horarioChegada;
	}

	public void setHorarioChegada(String horarioChegada) {
		this.horarioChegada = horarioChegada;
	}

	public Boolean getIsShare() {
		return Boolean.valueOf(this.isShare);
	}

	public void setIsShare(Boolean bool) {
		this.isShare = bool ? "1" : "0";
	}
}
