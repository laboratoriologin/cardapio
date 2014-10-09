package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="empresa")
public final class Empresa extends RestModel {

	@FormParam("cnpj")
	private String cnpj;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj=cnpj;
	}

	@FormParam("email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	@FormParam("endereco")
	private String endereco;

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco=endereco;
	}

	@FormParam("htmlempresa")
	private String htmlEmpresa;

	public String getHtmlEmpresa() {
		return htmlEmpresa;
	}

	public void setHtmlEmpresa(String htmlEmpresa) {
		this.htmlEmpresa=htmlEmpresa;
	}

	@FormParam("keycardapio")
	private String keyCardapio;

	public String getKeyCardapio() {
		return keyCardapio;
	}

	public void setKeyCardapio(String keyCardapio) {
		this.keyCardapio=keyCardapio;
	}

	@FormParam("keymobile")
	private String keyMobile;

	public String getKeyMobile() {
		return keyMobile;
	}

	public void setKeyMobile(String keyMobile) {
		this.keyMobile=keyMobile;
	}

	@FormParam("latitude")
	private Double latitude;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude=latitude;
	}

	@FormParam("longitude")
	private Double longitude;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude=longitude;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	@FormParam("telefone")
	private String telefone;

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone=telefone;
	}

	public Empresa(){}

	public Empresa(String id){
		this.id = Long.valueOf(id);
	}
}