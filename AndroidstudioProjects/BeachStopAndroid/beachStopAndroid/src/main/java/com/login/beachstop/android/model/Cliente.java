package com.login.beachstop.android.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;

@Table(name = "CLIENTE")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CELULAR")
	private String celular;

	@Column(name = "DATA_NASCIMENTO")
	private String dataNascimento;

	@Column(name = "TOKEN_ANDROID")
	private String tokenAndroid;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTokenAndroid() {
		return tokenAndroid;
	}

	public void setTokenAndroid(String tokenAndroid) {
		this.tokenAndroid = tokenAndroid;
	}
}
