package com.login.beachstop.garcom.android.models;

/**
 * Created by Argus on 14/01/2015.
 */
public class AcaoConta extends Base {

    private Long id;
    private String serviceName;
    private Acao acao;
    private Conta conta;
    private String horarioAtendimento;
    private String horarioSolicitacao;
    private Usuario usuario;

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

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }

    public String getHorarioSolicitacao() {
        return horarioSolicitacao;
    }

    public void setHorarioSolicitacao(String horarioSolicitacao) {
        this.horarioSolicitacao = horarioSolicitacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcaoConta acaoConta = (AcaoConta) o;

        if (id != null ? !id.equals(acaoConta.id) : acaoConta.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
