package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@SuppressWarnings("serial")
@XmlRootElement(name ="usuario")
public final class Usuario extends RestModel {

	@FormParam("celular")
	private String celular;

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular=celular;
	}

	@FormParam("contato")
	private String contato;

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato=contato;
	}

	@FormParam("cpf")
	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf=cpf;
	}

	@FormParam("datanascimento")
	private Date dataNascimento;

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento=dataNascimento;
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

	@FormParam("flagativo")
	private Boolean flagAtivo;

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo=flagAtivo;
	}

	@FormParam("grupousuario")
	private GrupoUsuario grupoUsuario;

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario=grupoUsuario;
	}

	@FormParam("login")
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login=login;
	}

	@FormParam("nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	@FormParam("rg")
	private String rg;

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg=rg;
	}

	@FormParam("senha")
	private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha=senha;
	}

	@FormParam("telefone")
	private String telefone;

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone=telefone;
	}

	@FormParam("telefonecontato")
	private String telefoneContato;

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato=telefoneContato;
	}

	public Usuario(){}

	public Usuario(String id){
		this.id = Long.valueOf(id);
	}
}