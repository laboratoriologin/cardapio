package com.login.cardapio.model;

import java.util.Date;
import java.util.Set;

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

@SuppressWarnings("serial")
@Entity
@Table(name = "publicidades")
public class Publicidade extends TSActiveRecordAb<Publicidade> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	private String imagem;

	private String link;

	@Column(name = "vigencia_inicial")
	private Date vigenciaInicial;

	@Column(name = "vigencia_final")
	private Date vigenciaFinal;

	@ManyToOne
	@JoinColumn(name = "tipo_publicidade_id")
	private TipoPublicidade tipoPublicidade;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "publicidade", cascade = CascadeType.ALL)
	private Set<Agenda> agenda;

	public Publicidade() {

	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public TipoPublicidade getTipoPublicidade() {
		return tipoPublicidade;
	}

	public void setTipoPublicidade(TipoPublicidade tipoPublicidade) {
		this.tipoPublicidade = tipoPublicidade;
	}

	public Set<Agenda> getAgenda() {
		return agenda;
	}

	public void setAgenda(Set<Agenda> agenda) {
		this.agenda = agenda;
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
		Publicidade other = (Publicidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
