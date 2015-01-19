package br.com.login.cardapio.beachstop.ws.model;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

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
	
	@Form(prefix = "setores")
	private List<Setor> setores;

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public UsuarioSetor(){}

	public UsuarioSetor(String id){
		this.id = Long.valueOf(id);
	}
}