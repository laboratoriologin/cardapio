package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="categoriacardapio")
public final class CategoriaCardapio extends RestModel {

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

	public CategoriaCardapio(){}

	public CategoriaCardapio(String id){
		this.id = Long.valueOf(id);
	}
}