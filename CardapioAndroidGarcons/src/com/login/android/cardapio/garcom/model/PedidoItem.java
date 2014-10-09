package com.login.android.cardapio.garcom.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "PEDIDO_ITEM")
public class PedidoItem implements Serializable {

	@PrimaryKey
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_PEDIDO")
	private Long idPedido;

	@Column(name = "ID_SUB_ITEM")
	private Long idSubItem;

	@Column(name = "QUANTIDADE")
	private Long quantidade;

	@Transient
	private String valor_total;

	@Transient
	private ItemCardapio itemCardapio;

	@Transient
	private ItemCardapioSubItem subItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdSubItem() {
		return idSubItem;
	}

	public void setIdSubItem(Long idSubItem) {
		this.idSubItem = idSubItem;
	}

	public ItemCardapioSubItem getSubItem() {
		return subItem;
	}

	public void setSubItem(ItemCardapioSubItem subItem) {
		this.subItem = subItem;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public void addQtd(Long qtd) {
		this.quantidade += qtd;
	}

	public void subQtd(Long qtd) {
		this.quantidade -= qtd;
	}

	/**
	 * @return the valor_total
	 */
	public String getValor_total() {
		return valor_total;
	}

	/**
	 * @param valor_total
	 *            the valor_total to set
	 */
	public void setValor_total(String valor_total) {
		this.valor_total = valor_total;
	}

	public ItemCardapio getItemCardapio() {
		return itemCardapio;
	}

	public void setItemCardapio(ItemCardapio itemCardapio) {
		this.itemCardapio = itemCardapio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subItem == null) ? 0 : subItem.hashCode());
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
		PedidoItem other = (PedidoItem) obj;
		if (subItem == null) {
			if (other.subItem != null)
				return false;
		} else if (!subItem.equals(other.subItem))
			return false;
		return true;
	}
}
