package com.login.cardapio.model;

import java.util.Set;

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

@SuppressWarnings("serial")
@Entity
@Table(name = "agendas_publicidade")
public class Agenda extends TSActiveRecordAb<Agenda> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_tipo_agenda")
	private TipoAgenda tipoAgenda;

	private String valor;

	@ManyToOne
	@JoinColumn(name = "id_publicidade")
	private Publicidade publicidade;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Publicidade getPublicidade() {
		return publicidade;
	}

	public void setPublicidade(Publicidade publicidade) {
		this.publicidade = publicidade;
	}
}
