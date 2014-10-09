package com.login.android.cardapio.garcom.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "CHAVE_CARDAPIO_EMPRESA")
public class ChaveCardapioEmpresa implements Serializable {

	@Column(name = "CHAVE")
	private String chave;
	
	@Transient
	private Long qteMesa;

	public ChaveCardapioEmpresa() {
		// TODO Auto-generated constructor stub
	}

	public ChaveCardapioEmpresa(String _chave, Long _qtdMesa) {
		this.chave = _chave;
		this.qteMesa = _qtdMesa;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	/**
	 * @return the qteMesa
	 */
	public Long getQteMesa() {
		return qteMesa;
	}

	/**
	 * @param qteMesa
	 *            the qteMesa to set
	 */
	public void setQteMesa(Long qteMesa) {
		this.qteMesa = qteMesa;
	}
}
