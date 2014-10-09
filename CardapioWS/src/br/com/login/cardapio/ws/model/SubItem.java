package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name = "subitem")
public final class SubItem extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@FormParam("item")
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@FormParam("tipoquantidade")
	private TipoQuantidade tipoQuantidade;

	public TipoQuantidade getTipoQuantidade() {
		return tipoQuantidade;
	}

	public void setTipoQuantidade(TipoQuantidade tipoQuantidade) {
		this.tipoQuantidade = tipoQuantidade;
	}

	@FormParam("quantidade")
	private Integer quantidade;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@FormParam("valor")
	private Double valor;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@FormParam("codsubitem")
	private String codSubItem;

	public String getCodSubItem() {
		return this.codSubItem;
	}

	public void setCodSubItem(String codSubItem) {
		this.codSubItem = codSubItem;
	}

	public SubItem() {
	}

	public SubItem(String id) {
		this.id = Long.valueOf(id);
	}
}