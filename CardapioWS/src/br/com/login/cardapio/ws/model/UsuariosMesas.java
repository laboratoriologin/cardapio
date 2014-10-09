package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

@SuppressWarnings("serial")
@XmlRootElement(name = "usuariosmesas")
public final class UsuariosMesas extends RestModel {

	@Form(prefix = "usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@FormParam("numeromesa")
	private Long numeroMesa;

	public Long getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(Long numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	@FormParam("listanumeromesa")
	private String listNMesa;

	public String getListNMesa() {
		return listNMesa;
	}

	public void setListNMesa(String listNMesa) {
		this.listNMesa = listNMesa;
	}

	public UsuariosMesas() {
	}

	public UsuariosMesas(String id) {
		this.id = Long.valueOf(id);
	}
}