package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="usuariosetor")
public final class UsuarioSetor extends RestModel {

	@FormParam("setor")
	private Setor setor;

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor=setor;
	}

	@FormParam("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}

	public UsuarioSetor(){}

	public UsuarioSetor(String id){
		this.id = Long.valueOf(id);
	}
}