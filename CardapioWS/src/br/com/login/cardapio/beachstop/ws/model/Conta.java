package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.Form;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@XmlRootElement(name = "conta")
public final class Conta extends RestModel {

	@FormParam("cliente")
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@FormParam("dataabertura")
	private Date dataAbertura;

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	@FormParam("datafechamento")
	private Date dataFechamento;

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	@FormParam("numero")
	private Integer numero;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Form(prefix = "setor")
	private Setor setor;

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	@FormParam("qtdpessoa")
	private Integer qtdPessoa;

	public Integer getQtdPessoa() {
		return qtdPessoa;
	}

	public void setQtdPessoa(Integer qtdPessoa) {
		this.qtdPessoa = qtdPessoa;
	}
	
	@FormParam("valor")
	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@FormParam("valorPago")
	private BigDecimal valorPago;

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}	

	@FormParam("tipoconta")
	private Boolean tipoConta;

	public Boolean getTipoConta() {
		return tipoConta;
	}
	
	public void setTipoConta(Boolean tipoConta) {
		this.tipoConta = tipoConta;
	}

	@FormParam("pedidosubitens")
	private List<PedidoSubItem> pedidoSubItens;

	public List<PedidoSubItem> getPedidoSubItens() {
		return pedidoSubItens;
	}

	public void setPedidoSubItens(List<PedidoSubItem> pedidoSubItens) {
		this.pedidoSubItens = pedidoSubItens;
	}

	public Conta() {
	}

	public Conta(String id) {
		this.id = Long.valueOf(id);
	}
}