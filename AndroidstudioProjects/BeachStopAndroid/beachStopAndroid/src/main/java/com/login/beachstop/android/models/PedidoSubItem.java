package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 30/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "PEDIDO_SUB_ITEM")
public class PedidoSubItem extends Base {

    @PrimaryKey
    @Column(name = "ID")
    protected Long id;

    @Transient
    protected String serviceName;

    @Column(name = "PEDIDO_ID")
    private Long pedidoId;

    @Column(name = "SUB_ITEM_ID")
    private Long subItemId;

    @Column(name = "QUANTIDADE")
    private Long quantidade;

    @Column(name = "KIT_ID")
    private Long kitId;

    @Transient
    private String valorUnitario;

    @Transient
    private String valorTotal;

    @Transient
    private SubItem subItem;

    @Transient
    private Kit kit;

    public PedidoSubItem() {
        this.setServiceName("pedidosubitens");
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {

        this.id = id;

    }

    @Override
    public String getServiceName() {

        return this.serviceName;

    }

    @Override
    public void setServiceName(String serviceName) {

        this.serviceName = serviceName;

    }

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public SubItem getSubItem() {
        return subItem;
    }

    public void setSubItem(SubItem subItem) {
        this.subItem = subItem;
    }

    public void addQtd(Long qtd) {
        this.quantidade += qtd;
    }

    public void subQtd(Long qtd) {
        this.quantidade -= qtd;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Long getKitId() {
        return kitId;
    }

    public void setKitId(Long kitId) {
        this.kitId = kitId;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedidoSubItem that = (PedidoSubItem) o;

        if (subItemId != null ? !subItemId.equals(that.subItemId) : that.subItemId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return subItemId != null ? subItemId.hashCode() : 0;
    }
}