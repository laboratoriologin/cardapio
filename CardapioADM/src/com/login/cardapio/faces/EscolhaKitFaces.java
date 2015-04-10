package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Kit;
import com.login.cardapio.util.Utilitarios;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EscolhaKitFaces extends TSMainFaces {

	private List<Kit> listKit;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		this.setListKit(new Kit().findAll("nome"));

	}

	@Override
	protected String update() throws TSApplicationException {
		
		try {
			Utilitarios.gerarNovoCodigoCardapio();
		} catch (TSApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage("Erro no sistema, entre em contato com o administrador, Erro: 0101!");
		}

		for (Kit kit : this.listKit) {
			kit.update();
		}

		this.clearFields();

		return null;

	}

	public List<Kit> getListKit() {
		return listKit;
	}

	public void setListKit(List<Kit> listKit) {
		this.listKit = listKit;
	}
}
