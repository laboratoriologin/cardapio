package com.login.beachstop.garcom.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 06/01/2015.
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

    @Column(name = "KIT_ID")
    private long kitId;

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
