package com.login.beachstop.android.model;

import java.io.Serializable;
import java.util.List;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "ITEM_CARDAPIO")
public class ItemCardapio implements Serializable {

	@PrimaryKey
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "GUARNICOES")
	private String guarnicoes;
	
	@Column(name = "INGREDIENTES")
	private String ingredientes;
	
	@Column(name = "IMAGEM")
	private String imagem;
	
	@Column(name = "TEMPO_MEDIO_PREPARO")
	private Long tempoMedioPreparo;
	
	@Column(name = "ID_CATEGORIA_CARDAPIO")
	private Long idCategoriaCardapio;
	
	@Transient
	private List<ItemCardapioSubItem> subItens;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getGuarnicoes() {
		return guarnicoes;
	}

	public void setGuarnicoes(String guarnicoes) {
		this.guarnicoes = guarnicoes;
	}

	public String getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<ItemCardapioSubItem> getSubItens() {
		return subItens;
	}

	public void setSubItens(List<ItemCardapioSubItem> subItens) {
		this.subItens = subItens;
	}

	public Long getIdCategoriaCardapio() {
		return idCategoriaCardapio;
	}

	public void setIdCategoriaCardapio(Long idCategoriaCardapio) {
		this.idCategoriaCardapio = idCategoriaCardapio;
	}

	public Long getTempoMedioPreparo() {
		return tempoMedioPreparo;
	}

	public void setTempoMedioPreparo(Long tempoMedioPreparo) {
		this.tempoMedioPreparo = tempoMedioPreparo;
	}
}
