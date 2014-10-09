package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="pedidosubitem")
public final class PedidoSubItem extends RestModel {

	@FormParam("pedido")
	private Pedido pedido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido=pedido;
	}

	@FormParam("quantidade")
	private Integer quantidade;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade=quantidade;
	}

	@FormParam("subitem")
	private SubItem subItem;

	public SubItem getSubItem() {
		return subItem;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem=subItem;
	}

	@FormParam("valorunitario")
	private Double valorUnitario;

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario=valorUnitario;
	}

	public PedidoSubItem(){}

	public PedidoSubItem(String id){
		this.id = Long.valueOf(id);
	}
}