package com.login.cardapio.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.login.cardapio.model.CategoriaCardapio;

import br.com.topsys.util.TSUtil;

@FacesConverter(value = "categoriaCardapio")
public class CategoriaCardapioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		if (TSUtil.isEmpty(arg2)) {
			return null;
		}

		CategoriaCardapio cardapio = new CategoriaCardapio();

		cardapio.setDescricao(arg2);

		return cardapio.getByModel("descricao");

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (TSUtil.isEmpty(arg2)) {
			return null;
		}
		
		Long id = Long.parseLong(arg2.toString());

		CategoriaCardapio cardapio = new CategoriaCardapio();

		cardapio.setId(id);

		return cardapio.getById().getDescricao();

	}

}
