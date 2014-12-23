package com.login.cardapio.model;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@Table(name = "sub_itens")
public class SubItem extends TSActiveRecordAb<SubItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private BigDecimal valor;

	private String descricao;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	private String codigo;

	private Long ordem;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "subItem", cascade = CascadeType.ALL)
	private List<KitSubItem> listKitSubItem;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public List<KitSubItem> getListKit() {
		return listKitSubItem;
	}

	public void setListKit(List<KitSubItem> listKitSubItem) {
		this.listKitSubItem = listKitSubItem;
	}

	public List<SubItem> findByItem() {

		Long itemFiltro = TSUtil.isEmpty(this.item) ? null : TSUtil.tratarLong(this.item.getId());

		return this.find("SELECT s FROM SubItem s, Item i where s.item.id = i.id and i.id = coalesce(?,i.id)", "s.nome", itemFiltro);

	}

	public List<String> findByNomeItemSubItem(String query) {

		List<String> listNomeItem = new ArrayList<String>();

		String nome = TSUtil.isEmpty(query) ? null : "%" + query + "%";

		List<SubItem> listSubItem = this.find("SELECT s FROM SubItem s, Item i where s.item.id = i.id and (i.nome + ' - ' + s.nome) like coalesce(?,(i.nome + ' - ' + s.nome)) ", "i.nome", nome);

		for (SubItem subItem : listSubItem) {
			listNomeItem.add(subItem.getId() + " - " + subItem.getItem().getNome() + " - " + subItem.getNome());
		}

		return listNomeItem;
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
		SubItem other = (SubItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
