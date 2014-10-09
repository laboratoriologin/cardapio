package com.login.cardapio.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "contas")
public class Conta extends TSActiveRecordAb<Conta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Column(name = "tipo_conta")
	private Boolean tipoConta;

	@Column(name = "data_abertura")
	private Date dataAbertura;

	@Column(name = "data_fechamento")
	private Date dataFechamento;

	private Long numero;

	@Column(name = "qtd_pessoa")
	private Long qtdPessoa;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<AcaoConta> listAcaoConta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Boolean getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(Boolean tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getQtdPessoa() {
		return qtdPessoa;
	}

	public void setQtdPessoa(Long qtdPessoa) {
		this.qtdPessoa = qtdPessoa;
	}

	public List<AcaoConta> getListAcaoConta() {
		return listAcaoConta;
	}

	public void setListAcaoConta(List<AcaoConta> listAcaoConta) {
		this.listAcaoConta = listAcaoConta;
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
		Conta other = (Conta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
