package com.login.cardapio.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.login.cardapio.model.Mesa;
import com.login.cardapio.model.Setor;

@ViewScoped
@ManagedBean(name = "setorFaces")
public class SetorFaces extends CrudFaces<Setor> {

	private static final long serialVersionUID = 1L;
	private Long mesaMenor;
	private Long mesaMaior;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}

	@Override
	protected void prePersist() {
		super.prePersist();

		getCrudModel().setListMesa(new ArrayList<Mesa>());
		Mesa mesa;

		if (mesaMenor != 0 && mesaMaior != 0 && mesaMenor < mesaMaior) {
			for (Long i = mesaMenor; i <= mesaMaior; i++) {

				mesa = new Mesa();
				mesa.setNumero(i);
				mesa.setSetor(getCrudModel());

				getCrudModel().getListMesa().add(mesa);

			}
		} else {
			this.addErrorMessage("Operação não permitida!");
		}
	}

	@Override
	public String limparPesquisa() {
		String retorno = super.limparPesquisa();
		return retorno;
	}

	public Long getMesaMenor() {
		return mesaMenor;
	}

	public void setMesaMenor(Long mesaMenor) {
		this.mesaMenor = mesaMenor;
	}

	public Long getMesaMaior() {
		return mesaMaior;
	}

	public void setMesaMaior(Long mesaMaior) {
		this.mesaMaior = mesaMaior;
	}
}
