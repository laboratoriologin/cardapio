package com.login.beachstop.android.models;

/**
 * Created by Argus on 13/01/2015.
 */
public class AcaoConta extends Base {

    protected Long id;
    protected String serviceName;

    private Long acaoId;
    private Conta conta;

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
}
