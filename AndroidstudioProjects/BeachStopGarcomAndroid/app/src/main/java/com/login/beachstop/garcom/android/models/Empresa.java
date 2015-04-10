package com.login.beachstop.garcom.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 22/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "EMPRESA")
public class Empresa extends Base {

    @PrimaryKey
    @Column(name = "ID")
    protected Long id;

    @Transient
    protected String serviceName;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "KEY_MOBILE")
    private String keyMobile;

    @Column(name = "HTML_EMPRESA")
    private String html;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "KEY_CARDAPIO")
    private String keyCardapio;

    public Empresa() {
        setServiceName("empresas");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getKeyMobile() {
        return keyMobile;
    }

    public void setKeyMobile(String keyMobile) {
        this.keyMobile = keyMobile;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKeyCardapio() {
        return keyCardapio;
    }

    public void setKeyCardapio(String keyCardapio) {
        this.keyCardapio = keyCardapio;
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
