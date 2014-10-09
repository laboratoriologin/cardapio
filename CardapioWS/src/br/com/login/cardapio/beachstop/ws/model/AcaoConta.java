package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

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

	@FormParam("hararioatendimento")
	private Date hararioAtendimento;

	public Date getHararioAtendimento() {
		return hararioAtendimento;
	}

	public void setHararioAtendimento(Date hararioAtendimento) {
		this.hararioAtendimento=hararioAtendimento;
	}

	@FormParam("horariosolocitacao")
	private Date horarioSolocitacao;

	public Date getHorarioSolocitacao() {
		return horarioSolocitacao;
	}

	public void setHorarioSolocitacao(Date horarioSolocitacao) {
		this.horarioSolocitacao=horarioSolocitacao;
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