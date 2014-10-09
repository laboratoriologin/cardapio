package br.com.login.cardapio.ws.model;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name = "item")
public final class Item extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@FormParam("guarnicoes")
	private String guarnicoes;

	public String getGuarnicoes() {
		return guarnicoes;
	}

	public void setGuarnicoes(String guarnicoes) {
		this.guarnicoes = guarnicoes;
	}

	@FormParam("empresacategoriacardapio")
	private EmpresaCategoriaCardapio empresaCategoriaCardapio;

	public EmpresaCategoriaCardapio getEmpresaCategoriaCardapio() {
		return empresaCategoriaCardapio;
	}

	public void setEmpresaCategoriaCardapio(EmpresaCategoriaCardapio empresaCategoriaCardapio) {
		this.empresaCategoriaCardapio = empresaCategoriaCardapio;
	}

	@FormParam("imagem")
	private String imagem;

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@FormParam("ingredientes")
	private String ingredientes;

	public String getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@FormParam("tempomediopreparo")
	private Integer tempoMedioPreparo;

	public Integer getTempoMedioPreparo() {
		return tempoMedioPreparo;
	}

	public void setTempoMedioPreparo(Integer tempoMedioPreparo) {
		this.tempoMedioPreparo = tempoMedioPreparo;
	}

	@FormParam("subitens")
	private List<SubItem> subItens;

	public List<SubItem> getSubItens() {
		return subItens;
	}

	public void setSubItens(List<SubItem> subItens) {
		this.subItens = subItens;
	}

	public Item() {
	}

	public Item(String id) {
		this.id = Long.valueOf(id);
	}
}