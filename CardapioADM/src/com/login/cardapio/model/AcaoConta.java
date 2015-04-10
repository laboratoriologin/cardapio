package com.login.cardapio.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "acoes_contas")
public class AcaoConta extends TSActiveRecordAb<AcaoConta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;

	@Column(name = "horario_solicitacao")
	private Date horarioSolicitado;

	@ManyToOne
	@JoinColumn(name = "acao_id")
	private Acao acao;

	@Column(name = "horario_atendimento")
	private Date horarioAtendimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getHorarioSolicitado() {
		return horarioSolicitado;
	}

	public void setHorarioSolicitado(Date horarioSolicitado) {
		this.horarioSolicitado = horarioSolicitado;
	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	public Date getHorarioAtendimento() {
		return horarioAtendimento;
	}

	public void setHorarioAtendimento(Date horarioAtendimento) {
		this.horarioAtendimento = horarioAtendimento;
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
		AcaoConta other = (AcaoConta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
