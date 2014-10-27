package com.login.beachstop.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;

/**
 * Created by Argus on 22/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "CLIENTE")
public class Cliente extends Base {

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CELULAR")
    private String celular;

    @Column(name = "DATA_NASCIMENTO")
    private String dataNascimento;

    @Column(name = "TOKEN")
    private String token;

    public Cliente() {
        setServiceName("clientes");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
