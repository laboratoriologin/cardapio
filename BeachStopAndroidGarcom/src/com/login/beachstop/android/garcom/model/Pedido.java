package com.login.beachstop.android.garcom.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.login.beachstop.android.garcom.util.Utilitarios;

@SuppressWarnings("serial")
@Table(name = "PEDIDO")
public class Pedido implements Serializable {

	@PrimaryKey
	@Column(name = "ID")
	private Long id;

	@Column(name = "OBSERVACAO")
	private String observacao;

	@Column(name = "ID_CONTA")
	private Long idConta;

	@Column(name = "FINALIZADO")
	private Integer finalizado;

	@Transient
	private List<PedidoItem> listPedidoItem;

	@Transient
	private Integer mesa;

	@Transient
	private String horario;

	@Transient
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public List<PedidoItem> getListPedidoItem() {
		return listPedidoItem;
	}

	public void setListPedidoItem(List<PedidoItem> listPedidoItem) {
		this.listPedidoItem = listPedidoItem;
	}

	public Integer getFinalizado() {
		return finalizado;
	}

	public Boolean getFinalizadoSys() {
		return finalizado == 1 ? true : false;
	}

	public void setFinalizado(Integer finalizado) {
		this.finalizado = finalizado;
	}

	public void setFinalizadoSys(Boolean finalizado) {
		this.finalizado = finalizado ? 1 : 0;
	}

	public Pedido() {

	}

	public Pedido(JSONObject jsonPedido, Integer mesa) {

		try {

			this.id = jsonPedido.getLong("id");

			this.horario = jsonPedido.getString("horario");

			this.observacao = jsonPedido.getString("observacao");

			this.mesa = mesa;

			this.listPedidoItem = new ArrayList<PedidoItem>();

			PedidoItem pedidoItem = null;

			JSONArray itens = Utilitarios.getAlwaysJsonArray(jsonPedido, "listPedidoSubItem");

			for (int i = 0; i < itens.length(); i++) {

				pedidoItem = new PedidoItem();

				pedidoItem.setId(itens.getJSONObject(i).getLong("id"));

				pedidoItem.setSubItem(new ItemCardapioSubItem());

				pedidoItem.getSubItem().setId(itens.getJSONObject(i).getJSONObject("subitem").getLong("id"));

				pedidoItem.getSubItem().setQuantidade(itens.getJSONObject(i).getLong("quantidade"));

				pedidoItem.getSubItem().setDescricao(itens.getJSONObject(i).getJSONObject("subitem").getString("descricao"));

				pedidoItem.setItemCardapio(new ItemCardapio());

				pedidoItem.getItemCardapio().setId(itens.getJSONObject(i).getJSONObject("subitem").getJSONObject("item").getLong("id"));

				pedidoItem.getItemCardapio().setNome(itens.getJSONObject(i).getJSONObject("subitem").getJSONObject("item").getString("nome"));

				pedidoItem.getSubItem().setValor(new BigDecimal(itens.getJSONObject(i).getJSONObject("subitem").getString("valor")));

				this.listPedidoItem.add(pedidoItem);

			}

		} catch (JSONException e) {

			e.printStackTrace();

		}

	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Integer getMesa() {
		return mesa;
	}

	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
