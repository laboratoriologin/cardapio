package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name = "publicidade")
public final class Publicidade extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@FormParam("empresa")
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@FormParam("tipopublicidade")
	private TipoPublicidade tipoPublicidade;

	public TipoPublicidade getTipoPublicidade() {
		return tipoPublicidade;
	}

	public void setTipoPublicidade(TipoPublicidade tipoPublicidade) {
		this.tipoPublicidade = tipoPublicidade;
	}

	@FormParam("imagem")
	private String imagem;

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@FormParam("link")
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@FormParam("preco")
	private Double preco;

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@FormParam("vigenciafinal")
	private Date vigenciaFinal;

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	@FormParam("vigenciainicial")
	private Date vigenciaInicial;

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Publicidade() {
	}

	public Publicidade(String id) {
		this.id = Long.valueOf(id);
	}

	private Integer tipoAgenda;

	public Integer getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(Integer tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	private Integer valor;

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

}