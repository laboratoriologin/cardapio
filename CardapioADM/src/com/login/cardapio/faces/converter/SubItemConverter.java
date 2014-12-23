package com.login.cardapio.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.Item;
import com.login.cardapio.model.SubItem;

@FacesConverter(value = "subItemConverter")
public class SubItemConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		if (TSUtil.isEmpty(arg2)) {
			return null;
		}

		Item item = new Item();
		item.setNome(arg2.split(" - ")[1]);

		SubItem subItem = new SubItem();
		subItem.setId(new Long(arg2.split(" - ")[0]));
		subItem.setNome(arg2.split(" - ")[2]);
		subItem.setItem(item);

		return subItem.getById();
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (TSUtil.isEmpty(arg2)) {
			return null;
		}

		SubItem subItem = (SubItem) arg2;

		String nome = subItem.getId() + " - " + subItem.getItem().getNome() + " - " + subItem.getNome();

		return nome;

	}

}
