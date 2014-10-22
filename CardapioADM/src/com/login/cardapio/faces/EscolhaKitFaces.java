package com.login.cardapio.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

import com.login.cardapio.model.Kit;

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
