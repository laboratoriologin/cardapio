<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/model/Empresa.java
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
=======
package br.com.login.cardapio.ws.model;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name = "empresa")
public final class Empresa extends RestModel {

	@FormParam("cnpj")
	private String cnpj;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@FormParam("dadosempresa")
	private String dadosEmpresa;

	public String getDadosEmpresa() {
		return dadosEmpresa;
	}

	public void setDadosEmpresa(String dadosEmpresa) {
		this.dadosEmpresa = dadosEmpresa;
	}

	@FormParam("descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@FormParam("email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@FormParam("endereco")
	private String endereco;

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@FormParam("imagemlogo")
	private String imagemLogo;

	public String getImagemLogo() {
		return imagemLogo;
	}

	public void setImagemLogo(String imagemLogo) {
		this.imagemLogo = imagemLogo;
	}

	@FormParam("keycardapio")
	private String keyCardapio;

	public String getKeyCardapio() {
		return keyCardapio;
	}

	public void setKeyCardapio(String keyCardapio) {
		this.keyCardapio = keyCardapio;
	}

	@FormParam("keymobile")
	private String keyMobile;

	public String getKeyMobile() {
		return keyMobile;
	}

	public void setKeyMobile(String keyMobile) {
		this.keyMobile = keyMobile;
	}

	@FormParam("latitude")
	private Double latitude;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@FormParam("longitude")
	private Double longitude;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@FormParam("telefone")
	private String telefone;

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@FormParam("qtdmesa")
	private Long qtdMesa;

	public Long getQtdMesa() {
		return this.qtdMesa;
	}

	public void setQtdMesa(Long qtdMesa) {
		this.qtdMesa = qtdMesa;
	}

	@FormParam("contas")
	private List<Conta> contas;

	public List<Conta> getContas() {
		return this.contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public Empresa() {
	}

	public Empresa(Long idEmpresa) {
		this.id = idEmpresa;
	}

	public Empresa(String keyMobile) {
		this.keyMobile = keyMobile;
	}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/model/Empresa.java
}