package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Transient;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class Item extends Base {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;
    @Transient
    private String serviceName;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "INGREDIENTE")
    private String ingrediente;
    @Column(name = "IMAGEM")
    private String imagem;
    @Column(name = "TEMPO_PREPARO")
    private String tempoPreparo;
    @Column(name = "ORDEM")
    private String ordem;
    @Column(name = "CATEGORIA_ID")
    private Long categoriaId;
    @Transient
    private List<SubItem> subItens;

    public Item() {

        setServiceName("itens");

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public List<SubItem> getSubItens() {
        return subItens;
    }

    public void setSubItens(List<SubItem> subItens) {
        this.subItens = subItens;
    }
}
