package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;

/**
 * Created by Argus on 22/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "CATEGORIA")
public class Categoria extends Base {

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ORDEM")
    private Long ordem;

    @Column(name = "FLAG_ATIVO")
    private boolean flagAtivo;

    @Column(name = "AREA")
    private long area;

    @Column(name = "IMAGEM")
    private String imagem;

    public Categoria() {
        setServiceName("categorias");
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getOrdem() {
        return ordem;
    }

    public void setOrdem(Long ordem) {
        this.ordem = ordem;
    }

    public boolean isFlagAtivo() {
        return flagAtivo;
    }

    public void setFlagAtivo(boolean flagAtivo) {
        this.flagAtivo = flagAtivo;
    }

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
