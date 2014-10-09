package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="subitem")
public final class SubItem extends RestModel {

	@FormParam("codigo")
	private Integer codigo;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo=codigo;
	}

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	@FormParam("flagativo")
	private Boolean flagAtivo;

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo=flagAtivo;
	}

	@FormParam("item")
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item=item;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	@FormParam("ordem")
	private Integer ordem;

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem=ordem;
	}

	@FormParam("valor")
	private Double valor;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor=valor;
	}

	public SubItem(){}

	public SubItem(String id){
		this.id = Long.valueOf(id);
	}
}