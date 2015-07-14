package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 13/01/2015.
 */
@SuppressWarnings("serial")
@Table(name = "ACAO_CONTA")
public class AcaoConta extends Base {

    @PrimaryKey
    @Column(name = "ID")
    protected Long id;

    @Transient
    protected String serviceName;

    @Column(name = "ACAO_ID")
    private Long acaoId;

    @Transient
    private Conta conta;

    @Column(name = "CONTA_ID")
    private Long contaId;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "IS_AUTORIZADO")
    private Long isAutorizado;

    public AcaoConta() {
        setServiceName("acoes_contas");
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

    public Long getAcaoId() {
        return acaoId;
    }

    public void setAcaoId(Long acaoId) {
        this.acaoId = acaoId;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public Long getIsAutorizado() {
        return isAutorizado;
    }

    public Boolean isAutorizado() {
        return isAutorizado == 1;
    }

    public void setIsAutorizado(Long isAutorizado) {
        this.isAutorizado = isAutorizado;
    }

    public void setIsAutorizado(Boolean isAutorizado) {
        this.isAutorizado = isAutorizado ? 1l : 0l ;
    }
}
