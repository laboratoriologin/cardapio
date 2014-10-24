package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name ="publicidade")
public final class Publicidade extends RestModel {

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	@FormParam("imagem")
	private String imagem;

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem=imagem;
	}

	@FormParam("link")
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link=link;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	@FormParam("tipopublicidade")
	private TipoPublicidade tipoPublicidade;

	public TipoPublicidade getTipoPublicidade() {
		return tipoPublicidade;
	}

	public void setTipoPublicidade(TipoPublicidade tipoPublicidade) {
		this.tipoPublicidade=tipoPublicidade;
	}

	@FormParam("vigenciafinal")
	private Date vigenciaFinal;

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal=vigenciaFinal;
	}

	@FormParam("vigenciainicial")
	private Date vigenciaInicial;

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial=vigenciaInicial;
	}

	public Publicidade(){}

	public Publicidade(String id){
		this.id = Long.valueOf(id);
	}
}