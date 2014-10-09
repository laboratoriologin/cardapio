package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name ="log")
public final class Log extends RestModel {

	@FormParam("horario")
	private Date horario;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario=horario;
	}

	@FormParam("pedidosubitem")
	private PedidoSubItem pedidoSubItem;

	public PedidoSubItem getPedidoSubItem() {
		return pedidoSubItem;
	}

	public void setPedidoSubItem(PedidoSubItem pedidoSubItem) {
		this.pedidoSubItem=pedidoSubItem;
	}

	@FormParam("status")
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status=status;
	}

	@FormParam("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}

	public Log(){}

	public Log(String id){
		this.id = Long.valueOf(id);
	}
}