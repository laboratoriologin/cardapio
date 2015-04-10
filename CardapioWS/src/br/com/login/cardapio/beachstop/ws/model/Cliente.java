package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name ="cliente")
public final class Cliente extends RestModel {

	@FormParam("celular")
	private String celular;

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular=celular;
	}

	@FormParam("datanascimento")
	private Date dataNascimento;

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento=dataNascimento;
	}

	@FormParam("email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	@FormParam("tokenandroid")
	private String tokenAndroid;

	public String getTokenAndroid() {
		return tokenAndroid;
	}

	public void setTokenAndroid(String tokenAndroid) {
		this.tokenAndroid=tokenAndroid;
	}

	@FormParam("tokenios")
	private String tokenIos;

	public String getTokenIos() {
		return tokenIos;
	}

	public void setTokenIos(String tokenIos) {
		this.tokenIos=tokenIos;
	}

	public Cliente(){}

	public Cliente(String id){
		this.id = Long.valueOf(id);
	}
}