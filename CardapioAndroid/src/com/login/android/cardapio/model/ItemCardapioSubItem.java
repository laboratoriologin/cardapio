package com.login.android.cardapio.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "SUB_ITEM_CARDAPIO")
public class ItemCardapioSubItem implements Serializable {

	@PrimaryKey
	@Column(name = "ID")
	private Long id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "VALOR")
	private String valor;

	@Column(name = "QUALIDADE")
	private Long quantidade;

	@Column(name = "ID_TIPO_QUANTIDADE")
	private Long idTipoQuantidade;

	@Column(name = "DESCRICAO_TIPO_QUANTIDADE")
	private String descricaoTipoQuantidade;

	@Column(name = "ID_ITEM_CARDAPIO")
	private Long idItemCardapio;

	@Transient
	private Long qtdSelecionado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public BigDecimal getValorBigDecimal() {
		return new BigDecimal(valor);
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor.toString();
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long l) {
		this.quantidade = l;
	}

	public Long getIdTipoQuantidade() {
		return idTipoQuantidade;
	}

	public void setIdTipoQuantidade(Long idTipoQuantidade) {
		this.idTipoQuantidade = idTipoQuantidade;
	}

	public String getDescricaoTipoQuantidade() {
		return descricaoTipoQuantidade;
	}

	public void setDescricaoTipoQuantidade(String descricaoTipoQuantidade) {
		this.descricaoTipoQuantidade = descricaoTipoQuantidade;
	}

	public Long getIdItemCardapio() {
		return idItemCardapio;
	}

	public void setIdItemCardapio(Long idItemCardapio) {
		this.idItemCardapio = idItemCardapio;
	}

	public Long getQtdSelecionado() {
		return qtdSelecionado;
	}

	public void setQtdSelecionado(Long qtdSelecionado) {
		this.qtdSelecionado = qtdSelecionado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCardapioSubItem other = (ItemCardapioSubItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
