package br.com.login.cardapio.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="empresacategoriacardapio")
public final class EmpresaCategoriaCardapio extends RestModel {

	@FormParam("categoriacardapio")
	private CategoriaCardapio categoriaCardapio;

	public CategoriaCardapio getCategoriaCardapio() {
		return categoriaCardapio;
	}

	public void setCategoriaCardapio(CategoriaCardapio categoriaCardapio) {
		this.categoriaCardapio=categoriaCardapio;
	}

	@FormParam("empresa")
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa=empresa;
	}

	@FormParam("ordem")
	private Integer ordem;

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem=ordem;
	}

	public EmpresaCategoriaCardapio(){}

	public EmpresaCategoriaCardapio(String id){
		this.id = Long.valueOf(id);
	}
}