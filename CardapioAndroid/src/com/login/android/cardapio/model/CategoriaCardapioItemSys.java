package com.login.android.cardapio.model;

import java.io.Serializable;

public class CategoriaCardapioItemSys implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private int resourceImg;
	private int resourceImgTopoCardapio;
	private Class<?> classe;
	
	public CategoriaCardapioItemSys() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoriaCardapioItemSys(Long _id, String _nome, int _resourceImg, int _resourceImgTopoCardapio, Class<?> _classe) {
		this.id = _id;
		this.nome = _nome;
		this.resourceImg = _resourceImg;
		this.resourceImgTopoCardapio = _resourceImgTopoCardapio;
		this.classe = _classe;
	}

	public Class<?> getClasse() {
		return classe;
	}
	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	public int getResourceImg() {
		// TODO Auto-generated method stub
		return this.resourceImg;
	}

	public void setResourceImg(int resourceImg) {
		this.resourceImg = resourceImg;
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

	public int getResourceImgTopoCardapio() {
		return resourceImgTopoCardapio;
	}

	public void setResourceImgTopoCardapio(int resourceImgTopoCardapio) {
		this.resourceImgTopoCardapio = resourceImgTopoCardapio;
	}
	
	
	
}
