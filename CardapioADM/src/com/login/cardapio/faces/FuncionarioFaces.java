package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.login.cardapio.model.Funcionario;
import com.login.cardapio.model.TipoFuncionario;
import com.login.cardapio.util.UsuarioUtil;

@ViewScoped
@ManagedBean(name = "funcionarioFaces")
public class FuncionarioFaces extends CrudFaces<Funcionario> {
	
	private List<SelectItem> comboTipoFuncionario;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("id");
		this.getCrudModel().setTipoFuncionario(new TipoFuncionario());
		
		this.comboTipoFuncionario = super.initCombo(new TipoFuncionario().findAll("id"), "id", "descricao");
	}
	
	public String limpar() {
		super.limpar();	
		this.getCrudModel().setTipoFuncionario(new TipoFuncionario());
		
		return null;
	}
	
	protected void prePersist() {
		getCrudModel().setSenha(UsuarioUtil.getSenhaCriptografada(getCrudModel().getSenha()));
	}

	public List<SelectItem> getComboTipoFuncionario() {
		return comboTipoFuncionario;
	}

	public void setComboTipoFuncionario(List<SelectItem> comboTipoFuncionario) {
		this.comboTipoFuncionario = comboTipoFuncionario;
	}
}
