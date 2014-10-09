package com.login.cardapio.model;

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
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "itens")
public class Item extends TSActiveRecordAb<Item> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Column(name = "ingrediente")
	private String ingrediente;

	private String imagem;

	@Column(name = "tempo_preparo")
	private Long tempoPreparo;

	private Long ordem;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<SubItem> subItens;

	public Long getId() {
		return TSUtil.tratarLong(id);
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(String ingrediente) {
		this.ingrediente = ingrediente;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Long getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(Long tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	public Long getOrdem() {
		return ordem;
	}

	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<SubItem> getSubItens() {
		return subItens;
	}

	public void setSubItens(List<SubItem> subItens) {
		this.subItens = subItens;
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	// @Override
	// public List<Item> findByModel(String... fieldsOrderBy) {
	// return this.findByCategoria();
	// }
	//
	// public List<Item> findByCategoria() {
	//
	// String nomeFiltro = TSUtil.isEmpty(this.nome) ? null: "%" +
	// this.nome.toLowerCase() + "%";
	//
	// Long categoriaFiltro = TSUtil.isEmpty(this.empresaCategoriaCardapio) ?
	// null : TSUtil.tratarLong(this.empresaCategoriaCardapio.getId());
	//
	// return
	// this.find("SELECT i FROM Item i, EmpresaCategoriaCardapio ECC where i.empresaCategoriaCardapio.id = ECC.id and lower(i.nome) like coalesce(?,lower(i.nome)) and ECC.id = coalesce(?,ECC.id)","i.nome",
	// nomeFiltro,categoriaFiltro);
	//
	// }

}
