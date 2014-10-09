package com.login.android.cardapio.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;

@SuppressWarnings("serial")
@Table(name = "ITEM_CATEGORIA_CARDAPIO")
public class CategoriaCardapioItem implements Serializable {

	@Column(name = "ID")
	private Long id;

	public CategoriaCardapioItem() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
