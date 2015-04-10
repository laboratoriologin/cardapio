package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 19/12/2014.
 */
@SuppressWarnings("serial")
@Table(name = "KIT_SUB_ITEM")
public class KitSubItem extends Base {

    @PrimaryKey
    @Column(name = "ID")
    private long id;

    @Transient
    private String serviceName;

    @Column(name = "SUB_ITEM_ID")
    private long subItemId;

    @Transient
    private Item item;

    @Column(name = "KIT_ID")
    private long kitId;

    @Column(name = "QUANTIDADE")
    private long quantidade;

    public KitSubItem() {
        setServiceName("kits_sub_itens");
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

    public long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(long subItemId) {
        this.subItemId = subItemId;
    }

    public long getKitId() {
        return kitId;
    }

    public void setKitId(long kitId) {
        this.kitId = kitId;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KitSubItem that = (KitSubItem) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}