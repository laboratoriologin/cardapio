package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

import java.math.BigDecimal;

/**
 * Created by Argus on 29/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "SUB_ITEM")
public class SubItem extends Base {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;
    @Transient
    private String serviceName;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "ORDEM")
    private Long ordem;

    @Transient
    private Long qtdSelecionado;

    public SubItem() {

        setServiceName("subitens");

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


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor.toString();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getOrdem() {
        return ordem;
    }

    public void setOrdem(Long ordem) {
        this.ordem = ordem;
    }

    public Long getQtdSelecionado() {
        return qtdSelecionado;
    }

    public void setQtdSelecionado(Long qtdSelecionado) {
        this.qtdSelecionado = qtdSelecionado;
    }

    public BigDecimal getValorBigDecimal() {
        return new BigDecimal(valor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubItem subItem = (SubItem) o;

        if (id != null ? !id.equals(subItem.id) : subItem.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
