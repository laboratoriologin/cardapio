package com.login.android.cardapio.garcom.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

@SuppressWarnings("serial")
@Table(name = "EMPRESAS")
public class Empresa implements Serializable {
	
	@PrimaryKey
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "DADOS_EMPRESA")
	private String dadosEmpresa;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ENDERECO")
	private String endereco;

	@Column(name = "IMAGEM_LOGO")
	private String imagemLogo;

	@Column(name = "KEY_CARDAPIO")
	private String keyCardapio;
	
	@Column(name = "KEY_MOBILE")
	private String keyMobile;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result
				+ ((dadosEmpresa == null) ? 0 : dadosEmpresa.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imagemLogo == null) ? 0 : imagemLogo.hashCode());
		result = prime * result
				+ ((keyCardapio == null) ? 0 : keyCardapio.hashCode());
		result = prime * result
				+ ((keyMobile == null) ? 0 : keyMobile.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (dadosEmpresa == null) {
			if (other.dadosEmpresa != null)
				return false;
		} else if (!dadosEmpresa.equals(other.dadosEmpresa))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagemLogo == null) {
			if (other.imagemLogo != null)
				return false;
		} else if (!imagemLogo.equals(other.imagemLogo))
			return false;
		if (keyCardapio == null) {
			if (other.keyCardapio != null)
				return false;
		} else if (!keyCardapio.equals(other.keyCardapio))
			return false;
		if (keyMobile == null) {
			if (other.keyMobile != null)
				return false;
		} else if (!keyMobile.equals(other.keyMobile))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @return the dadosEmpresa
	 */
	public String getDadosEmpresa() {
		return dadosEmpresa;
	}

	/**
	 * @param dadosEmpresa the dadosEmpresa to set
	 */
	public void setDadosEmpresa(String dadosEmpresa) {
		this.dadosEmpresa = dadosEmpresa;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the imagemLogo
	 */
	public String getImagemLogo() {
		return imagemLogo;
	}

	/**
	 * @param imagemLogo the imagemLogo to set
	 */
	public void setImagemLogo(String imagemLogo) {
		this.imagemLogo = imagemLogo;
	}

	/**
	 * @return the keyCardapio
	 */
	public String getKeyCardapio() {
		return keyCardapio;
	}

	/**
	 * @param keyCardapio the keyCardapio to set
	 */
	public void setKeyCardapio(String keyCardapio) {
		this.keyCardapio = keyCardapio;
	}

	/**
	 * @return the keyMobile
	 */
	public String getKeyMobile() {
		return keyMobile;
	}

	/**
	 * @param keyMobile the keyMobile to set
	 */
	public void setKeyMobile(String keyMobile) {
		this.keyMobile = keyMobile;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "LATITUDE")
	private Double latitude;
	
	@Column(name = "LONGITUDE")
	private Double longitude;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "TELEFONE")
	private String telefone;

	public Empresa() {
	}

	
}
