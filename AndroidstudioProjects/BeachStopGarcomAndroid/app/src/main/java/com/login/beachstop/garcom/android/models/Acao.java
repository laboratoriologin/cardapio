package com.login.beachstop.garcom.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 29/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "ACAO")
public class Acao extends Base {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    @Transient
    private String serviceName;

    @Column(name = "DESCRICAO")
    private String descricao;

    public Acao() {
        setServiceName("acoes");
    }

    public Acao(Long id) {
        this();
        this.setId(id);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
