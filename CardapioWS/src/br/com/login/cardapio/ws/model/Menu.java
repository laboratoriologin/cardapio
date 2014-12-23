package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="menu")
public final class Menu extends RestModel {

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

	@FormParam("managedbeanreset")
	private String managedBeanReset;

	public String getManagedBeanReset() {
		return managedBeanReset;
	}

	public void setManagedBeanReset(String managedBeanReset) {
		this.managedBeanReset=managedBeanReset;
	}

	@FormParam("menu")
	private Menu menu;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu=menu;
	}

	@FormParam("ordem")
	private Integer ordem;

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem=ordem;
	}

	@FormParam("url")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url=url;
	}

	public Menu(){}

	public Menu(String id){
		this.id = Long.valueOf(id);
	}
}