package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.dao.SubItemDAO;
import br.com.login.cardapio.beachstop.ws.model.SubItem;

@Path("/sub_itens")
public class SubItemService extends RestService<SubItem> {

	@Override
	public void initDAO() {
		this.restDAO = new SubItemDAO();
	}

	@GET
	@Path("/autocomplete/{autocomplete}")
	@Produces("application/json; charset=UTF-8")
	public String getItensByAutoComplete(@PathParam(value = "autocomplete") String autoComplete) {
		List<SubItem> listRetorno = new SubItemDAO().getAll(autoComplete);
		String retorno = "[";

		for (SubItem subItem : listRetorno) {
			retorno += "{";
			retorno += "\"id\": \"" + subItem.getId() + "\"";
			retorno += ",\"label\": \"" + "(" + subItem.getCodigo() + ") " + subItem.getItem().getNome() + " - " + subItem.getNome() + "\"";
			retorno += ",\"value\": \"" + "(" + subItem.getCodigo() + ") " + subItem.getItem().getNome() + " - " + subItem.getNome() + "\"";
			retorno += "},";
		}
		
		retorno = retorno.substring(0, retorno.length() - 1);

		retorno += "]";
		return retorno;
	}
}
