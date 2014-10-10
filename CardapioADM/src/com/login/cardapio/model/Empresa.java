package com.login.cardapio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "empresas")
public class Empresa extends TSActiveRecordAb<Empresa> {
	/**
	 * Propriedade identificadora do objeto Grupo(Grupo de usuários).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Propriedade descrição do objeto Grupo(Grupo de usuários).
	 */
	private String descricao;
	/**
	 * Propriedade lista de permissões do objeto Grupo(Grupo de usuários).
	 */
//	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
//	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
//	private List<Permissao> permissoes;

	// @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	// @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	// private List<Metrica> metricas;

	@Column(name = "nome")
	private String nome;

	@Column(name = "key_mobile")
	private String keyMobile;

	@Column(name = "key_cardapio")
	private String keyCardapio;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "dados_empresa")
	private String dadosEmpresa;

	@Column(name = "email")
	private String email;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "quantidade_mesa")
	private Integer quantidadeMesa;

	/**
	 * Obtém a propriedade identificadora do objeto Grupo(Grupo de usuários).
	 * 
	 * @return id Inteiro longo. Identificador do objeto Grupo(Grupo de
	 *         usuários).
	 */
	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	/**
	 * Seta a prorpiedade Id do objeto Grupo(Grupo de usuários).
	 * 
	 * @param pId
	 *            Identificador do objeto Grupo(Grupo de usuários).
	 */
	public final void setId(final Long pId) {
		this.id = pId;
	}

	/**
	 * Obtém a propriedade descrição do objeto Grupo(Grupo de usuários).
	 * 
	 * @return String. Retorna a descrição do Grupo(Grupo de usuários).
	 */
	public final String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a propriedade descrição do objeto Grupo(Grupo de usuários).
	 * 
	 * @param pDescricao
	 *            String. Nome do objeto Grupo(Grupo de usuários).
	 */
	public final void setDescricao(final String pDescricao) {
		this.descricao = pDescricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getKeyMobile() {
		return keyMobile;
	}

	public void setKeyMobile(String keyMobile) {
		this.keyMobile = keyMobile;
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

	public String getDadosEmpresa() {
		return dadosEmpresa;
	}

	public void setDadosEmpresa(String dados_empresa) {
		this.dadosEmpresa = dados_empresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getKeyCardapio() {
		return keyCardapio;
	}

	public void setKeyCardapio(String keyCardapio) {
		this.keyCardapio = keyCardapio;
	}

	public Integer getQuantidadeMesa() {
		return quantidadeMesa;
	}

	public void setQuantidadeMesa(Integer quantidadeMesa) {
		this.quantidadeMesa = quantidadeMesa;
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
