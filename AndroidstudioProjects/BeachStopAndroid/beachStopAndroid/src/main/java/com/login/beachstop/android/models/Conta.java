package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 28/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "CONTA")
public class Conta extends Base {

    @Column(name = "ID")
    protected Long id;

    @Transient
    protected String serviceName;

    @Column(name = "CLIENTE_ID")
    private Long clienteId;

    @Column(name = "TIPO_CONTA")
    private Long tipoConta;

    @Column(name = "DATA_ABERTURA")
    private String dataAbertura;

    @Column(name = "DATA_FECHAMENTO")
    private String dataFechamento;

    @Column(name = "NUMERO")
    private Long numero;

    public Conta() {
        setServiceName("contas");
    }

    public Conta(Long id) {
        super();
        this.setId(id);
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(Long tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(String dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
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
}
