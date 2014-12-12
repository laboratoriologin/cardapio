package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

import java.util.List;

/**
 * Created by Argus on 30/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "PEDIDO")
public class Pedido extends Base {

    @PrimaryKey
    @Column(name = "ID")
    protected Long id;

    @Transient
    protected String serviceName;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @Column(name = "CONTA_ID")
    private Long contaId;

    @Column(name = "FINALIZADO")
    private Integer finalizado;

    @Transient
    private List<PedidoSubItem> pedidoSubItens;

    public Pedido() {
        this.setServiceName("pedidos");
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public List<PedidoSubItem> getPedidoSubItens() {
        return pedidoSubItens;
    }

    public void setPedidoSubItens(List<PedidoSubItem> pedidoSubItens) {
        this.pedidoSubItens = pedidoSubItens;
    }

    public Integer getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Integer finalizado) {
        this.finalizado = finalizado;
    }

    public Boolean getFinalizadoSys() {
        return finalizado == 1 ? true : false;
    }

    public void setFinalizadoSys(Boolean finalizado) {
        this.finalizado = finalizado ? 1 : 0;
    }
}
