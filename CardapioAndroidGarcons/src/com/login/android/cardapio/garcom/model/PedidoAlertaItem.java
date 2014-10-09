package com.login.android.cardapio.garcom.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
public class PedidoAlertaItem implements Serializable, Comparable<PedidoAlertaItem> {

	private Pedido pedido;
	private Alerta alerta;
	private String tempo;
	private boolean executando;

	public Long getId() {

		return 10L;

	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public String getHorario() {

		if (pedido != null) {
			return pedido.getHorario().replace("T", " ");
		}
		if (alerta != null) {
			return alerta.getHorario().replace("T", " ");
		}

		return "";

	}

	@Override
	public int compareTo(PedidoAlertaItem another) {

		return another.getHorario().compareTo(this.getHorario());

	}

	public Boolean isExecutando() {
		return executando;
	}

	public void setExecutando(boolean executando) {
		this.executando = executando;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alerta == null) ? 0 : alerta.hashCode());
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		PedidoAlertaItem other = (PedidoAlertaItem) obj;
			
		if(pedido!=null && other.pedido!=null) {
			return pedido.equals(other.pedido);
		}
		
		if(alerta!=null && other.alerta!=null) {
			return alerta.equals(other.alerta);
		}
		
		return false;
		
	}

}
