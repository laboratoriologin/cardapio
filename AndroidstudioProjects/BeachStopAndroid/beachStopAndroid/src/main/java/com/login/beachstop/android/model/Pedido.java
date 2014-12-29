package com.login.beachstop.android.model;

import java.io.Serializable;
import java.util.List;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "PEDIDO")
public class Pedido implements Serializable {

	@PrimaryKey
	@Column(name = "ID")
	private Long id;

	@Column(name = "OBSERVACAO")
	private String observacao;

	@Column(name = "ID_CONTA")
	private Long idConta;

	@Column(name = "FINALIZADO")
	private Integer finalizado;

	@Column(name = "IS_PEDIDO")
	private Long pedido;

	@Transient
	private List<PedidoItem> listPedidoItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public List<PedidoItem> getListPedidoItem() {
		return listPedidoItem;
	}

	public void setListPedidoItem(List<PedidoItem> listPedidoItem) {
		this.listPedidoItem = listPedidoItem;
	}

	public Integer getFinalizado() {
		return finalizado;
	}

	public Boolean getFinalizadoSys() {
		return finalizado == 1 ? true : false;
	}

	public void setFinalizado(Integer finalizado) {
		this.finalizado = finalizado;
	}

	public void setFinalizadoSys(Boolean finalizado) {
		this.finalizado = finalizado ? 1 : 0;
	}
}
