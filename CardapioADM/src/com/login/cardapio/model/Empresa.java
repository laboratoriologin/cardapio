package com.login.cardapio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "empresas")
public class Empresa extends TSActiveRecordAb<Empresa> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	// @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	// private List<Permissao> permissoes;

	// @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	// @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	// private List<Metrica> metricas;

	private String nome;

	private String cnpj;

	private String endereco;

	private String telefone;

	@Column(name = "key_mobile")
	private String keyMobile;

	@Column(name = "html_empresa")
	private String htmlEmpresa;

	private Double latitude;

	private Double longitude;

	private String email;

	@Column(name = "key_cardapio")
	private String keyCardapio;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getKeyMobile() {
		return keyMobile;
	}

	public void setKeyMobile(String keyMobile) {
		this.keyMobile = keyMobile;
	}

	public String getHtmlEmpresa() {
		return htmlEmpresa;
	}

	public void setHtmlEmpresa(String htmlEmpresa) {
		this.htmlEmpresa = htmlEmpresa;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKeyCardapio() {
		return keyCardapio;
	}

	public void setKeyCardapio(String keyCardapio) {
		this.keyCardapio = keyCardapio;
	}

	public Empresa getEmpresa() {

		List<Empresa> listEmpresa = findBySQL("select top 1 * from empresas order by id");

		if (listEmpresa.size() != 0) {
			return listEmpresa.get(0);
		} else {
			return new Empresa();
		}

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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
