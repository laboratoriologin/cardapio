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

@SuppressWarnings("serial")
@Entity
@Table(name = "itens")
public class Item extends TSActiveRecordAb<Item> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "nome")
	private String nome;

	@Column (name = "descricao")
	private String descricao;
	
	@Column (name = "guarnicoes")
	private String guarnicoes;
	
	@Column (name = "ingredientes")
	private String ingredientes;
	
	@Column (name = "imagem")
	private String imagem;
	
	@Column (name = "tempo_medio_preparo")
	private Long tempoMedioPreparo;
	
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<SubItem> subItens;
	
	@ManyToOne
	@JoinColumn (name = "id_empresa_categoria_cardapio")
	private EmpresaCategoriaCardapio empresaCategoriaCardapio;
	
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

	public String getGuarnicoes() {
		return guarnicoes;
	}

	public void setGuarnicoes(String guarnicoes) {
		this.guarnicoes = guarnicoes;
	}

	public String getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public EmpresaCategoriaCardapio getEmpresaCategoriaCardapio() {
		return empresaCategoriaCardapio;
	}

	public void setEmpresaCategoriaCardapio(EmpresaCategoriaCardapio empresaCategoriaCardapio) {
		this.empresaCategoriaCardapio = empresaCategoriaCardapio;
	}

	public List<SubItem> getSubItens() {
		return subItens;
	}

	public void setSubItens(List<SubItem> subItens) {
		this.subItens = subItens;
	}

	public Long getTempoMedioPreparo() {
		return tempoMedioPreparo;
	}

	public void setTempoMedioPreparo(Long tempoMedioPreparo) {
		this.tempoMedioPreparo = tempoMedioPreparo;
	} 
	@Override
	public List<Item> findByModel(String... fieldsOrderBy) {
		return this.findByCategoria();
	}
	
	public List<Item> findByCategoria() {
		
		String nomeFiltro = TSUtil.isEmpty(this.nome) ? null: "%" + this.nome.toLowerCase() + "%";
		
		Long categoriaFiltro = TSUtil.isEmpty(this.empresaCategoriaCardapio) ? null : TSUtil.tratarLong(this.empresaCategoriaCardapio.getId());
		
		return this.find("SELECT i FROM Item i, EmpresaCategoriaCardapio ECC where i.empresaCategoriaCardapio.id = ECC.id and lower(i.nome) like coalesce(?,lower(i.nome)) and ECC.id = coalesce(?,ECC.id)","i.nome", nomeFiltro,categoriaFiltro);
		
	}
	
}
