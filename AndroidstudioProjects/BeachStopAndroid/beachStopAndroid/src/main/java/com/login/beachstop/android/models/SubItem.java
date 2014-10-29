package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 29/10/2014.
 */
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
}
