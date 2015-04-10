package com.login.beachstop.garcom.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 29/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "MESA")
public class Mesa extends Base {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    @Transient
    private String serviceName;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "SETOR_ID")
    private Long setorId;

    private Setor setor;

    public Mesa() {
        setServiceName("mesas");
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

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
