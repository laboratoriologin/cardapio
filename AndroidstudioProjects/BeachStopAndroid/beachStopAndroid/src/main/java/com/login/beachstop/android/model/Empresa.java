package com.login.beachstop.android.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "CHAVE_CARDAPIO_EMPRESA")
public class Empresa implements Serializable {

	@Column(name = "CHAVE")
	private String chave;

	@Transient
	private Long qtdMesa;

	@Transient
	private String dadosEmpresa;

	public Empresa() {
		// TODO Auto-generated constructor stub
	}

	public Empresa(String _chave) {
		this.chave = _chave;
	}

	public Empresa(String keyCardapio, Long qtdMesa) {
		this.chave = keyCardapio;
		this.qtdMesa = qtdMesa;
	}

	public Empresa(String keyCardapio, Long qtdMesa, String dados) {
		this.chave = keyCardapio;
		this.qtdMesa = qtdMesa;
		this.dadosEmpresa = dados;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	/**
	 * @return the qtdMesa
	 */
	public Long getQtdMesa() {
		return qtdMesa;
	}

	/**
	 * @param qtdMesa
	 *            the qtdMesa to set
	 */
	public void setQtdMesa(Long qtdMesa) {
		this.qtdMesa = qtdMesa;
	}

	/**
	 * @return the dadosEmpresa
	 */
	public String getDadosEmpresa() {
		return dadosEmpresa;
	}

	/**
	 * @param dadosEmpresa
	 *            the dadosEmpresa to set
	 */
	public void setDadosEmpresa(String dadosEmpresa) {
		this.dadosEmpresa = dadosEmpresa;
	}
}
