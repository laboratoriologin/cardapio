package br.com.login.cardapio.beachstop.ws.model;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="kit")
public final class Kit extends RestModel {

	@FormParam("desconto")
	private Double desconto;

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto=desconto;
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

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	@FormParam("ordem")
	private Integer ordem;

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem=ordem;
	}
	
	@FormParam("kitsubitens")
	private List<KitSubItem> kitSubItens;
	
	public List<KitSubItem> getKitSubItens(){
		return this.kitSubItens;
	}
	
	public void setKitSubItens(List<KitSubItem> kitSubItens){
		this.kitSubItens = kitSubItens;
	}
	
	@FormParam("imagem")
	private String imagem;

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Kit(){}

	public Kit(String id){
		this.id = Long.valueOf(id);
	}
}