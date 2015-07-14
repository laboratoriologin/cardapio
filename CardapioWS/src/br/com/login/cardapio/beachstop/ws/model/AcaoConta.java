package br.com.login.cardapio.beachstop.ws.model;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;


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

	@Form(prefix = "conta")
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
	
	@FormParam("strHorarioSolicitacao")
	private String strHorarioSolicitacao;

	public String getStrHorarioSolicitacao() {
		return strHorarioSolicitacao;
	}

	public void setStrHorarioSolicitacao(String strHorarioSolicitacao) {
		this.strHorarioSolicitacao = strHorarioSolicitacao;
	}	

	@FormParam("strDifMinuto")
	private String strDifMinuto;
	
	public String getStrDifMinuto() {
		return strDifMinuto;
	}

	public void setStrDifMinuto(String strDifMinuto) {
		this.strDifMinuto = strDifMinuto;
	}	

	@FormParam("diffhorarioSolicitacao")
	private String diffHorarioSolitacao;

	public String getDiffHorarioSolitacao() {
		if(horarioSolicitacao != null){
	        Calendar cal = Calendar.getInstance();            
	        cal.setTime(horarioSolicitacao);        
	        return String.valueOf((int) (((System.currentTimeMillis() - cal.getTimeInMillis())/ (1000*60)) ));
		}else{
			return "";
		}
	}

	public void setDiffHorarioSolitacao(String diffHorarioSolitacao) {
		this.diffHorarioSolitacao=diffHorarioSolitacao;
	}

	@FormParam("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}
		
	@FormParam("pedido")
	private Pedido pedido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido=pedido;
	}
	
	@FormParam("numero")
	private String numero;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero=numero;
	}

	@FormParam("autorizacao")
	private Boolean autorizacao;

	public Boolean getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(Boolean autorizacao) {
		this.autorizacao = autorizacao;
	}
	
	@Form(prefix = "conta_destino")
	private Conta contaDestino;

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino=contaDestino;
	}
	
	public AcaoConta(){}

	public AcaoConta(String id){
		this.id = Long.valueOf(id);
	}
}