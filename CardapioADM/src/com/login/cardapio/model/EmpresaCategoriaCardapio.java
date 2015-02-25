package com.login.cardapio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@SuppressWarnings("serial")
@Entity
@Table(name = "empresas_categorias_cardapios")
public class EmpresaCategoriaCardapio extends TSActiveRecordAb<EmpresaCategoriaCardapio> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_categoria_cardapio")
	private CategoriaCardapio categoriaCardapio;

	private Integer ordem;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@Column(name = "peso_metrica")
	private Integer pesoMetrica;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoriaCardapio getCategoriaCardapio() {
		return categoriaCardapio;
	}

	public void setCategoriaCardapio(CategoriaCardapio categoriaCardapio) {
		this.categoriaCardapio = categoriaCardapio;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Integer getPesoMetrica() {
		return pesoMetrica;
	}

	public void setPesoMetrica(Integer pesoMetrica) {
		this.pesoMetrica = pesoMetrica;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public List<EmpresaCategoriaCardapio> findByEmpresa(Empresa empresa) {

		return this.find("SELECT ECC from EmpresaCategoriaCardapio ECC where ECC.empresa.id = ?", "id", empresa.getId());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriaCardapio == null) ? 0 : categoriaCardapio.hashCode());
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
		EmpresaCategoriaCardapio other = (EmpresaCategoriaCardapio) obj;
		if (categoriaCardapio == null) {
			if (other.categoriaCardapio != null)
				return false;
		} else if (!categoriaCardapio.equals(other.categoriaCardapio))
			return false;
		return true;
	}

}
