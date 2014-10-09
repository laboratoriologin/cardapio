package com.login.android.cardapio.garcom.model;

import java.io.Serializable;

public class CategoriaCardapioItemSys implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Class<?> classe;

	public CategoriaCardapioItemSys() {
		// TODO Auto-generated constructor stub
	}

	public CategoriaCardapioItemSys(Long _id, String _nome, Class<?> _classe) {
		this.id = _id;
		this.nome = _nome;
		this.classe = _classe;
	}

	public Class<?> getClasse() {
		return classe;
	}

	public void setClasse(Class<?> classe) {
		this.classe = classe;
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
}
