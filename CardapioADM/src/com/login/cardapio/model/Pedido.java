package com.login.cardapio.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "acoes")
public class Pedido extends TSActiveRecordAb<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String Observacao;

	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "peiddo", cascade = CascadeType.ALL)
	private PedidoSubItem listPedidoSubItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public PedidoSubItem getListPedidoSubItem() {
		return listPedidoSubItem;
	}

	public void setListPedidoSubItem(PedidoSubItem listPedidoSubItem) {
		this.listPedidoSubItem = listPedidoSubItem;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
