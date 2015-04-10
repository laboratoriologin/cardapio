package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

import java.util.List;

/**
 * Created by Argus on 28/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "CONTA")
public class Conta extends Base {

    @PrimaryKey
    @Column(name = "ID")
    protected Long id;

    @Column(name = "SISTEMA_ID")
    protected Long sistemaId;

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

    @Transient
    private List<Pedido> pedidos;

    @Transient
    private String valorTotal;

    @Transient
    private String valorTotalPago;

    public Conta() {

        setServiceName("contas");
    }

    public Conta(Long id) {
        this();
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(String valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public Long getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(Long sistemaId) {
        this.sistemaId = sistemaId;
    }
}
