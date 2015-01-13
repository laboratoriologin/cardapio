package br.com.login.cardapio.beachstop.ws.model;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("serial")
@XmlRootElement(name ="acaoconta")
public final class AcaoConta extends RestModel {

	@FormParam("acao")
	private Acao acao;

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao=acao;
	}

	@FormParam("conta")
	private Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta=conta;
	}

	@FormParam("horarioAtendimento")
	private Date horarioAtendimento;

	public Date getHorarioAtendimento() {
		return horarioAtendimento;
	}

	public void setHorarioAtendimento(Date horarioAtendimento) {
		this.horarioAtendimento=horarioAtendimento;
	}

	@FormParam("horarioSolicitacao")
	private Date horarioSolicitacao;

	public Date getHorarioSolicitacao() {
		return horarioSolicitacao;
	}

	public void setHorarioSolicitacao(Date horarioSolicitacao) {
		this.horarioSolicitacao=horarioSolicitacao;
	}

	@FormParam("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}

	public AcaoConta(){}

	public AcaoConta(String id){
		this.id = Long.valueOf(id);
	}
}