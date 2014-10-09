package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="categoria")
public final class Categoria extends RestModel {

	@FormParam("area")
	private Area area;

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area=area;
	}

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	@FormParam("flagativo")
	private Boolean flagAtivo;

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo=flagAtivo;
	}

	@FormParam("imagem")
	private String imagem;

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem=imagem;
	}

	@FormParam("ordem")
	private Integer ordem;

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem=ordem;
	}

	public Categoria(){}

	public Categoria(String id){
		this.id = Long.valueOf(id);
	}
}