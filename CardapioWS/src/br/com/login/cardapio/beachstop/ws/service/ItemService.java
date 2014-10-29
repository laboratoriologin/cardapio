package br.com.login.cardapio.beachstop.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.model.Categoria;
import br.com.login.cardapio.beachstop.ws.model.Item;
import br.com.login.cardapio.beachstop.ws.dao.ItemDAO;

@Path("/itens")
public class ItemService extends RestService<Item> {

	@Override
	public void initDAO() {
		this.restDAO = new ItemDAO();
	}

	@GET
	@Path("/categorias/{categorias}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public List<Item> getItensByCategoria(@PathParam(value = "categorias") String categorias, @PathParam(value = "fields") String fields) {

		List<Item> listRetorno = new ArrayList<Item>();
		Categoria categoria;

		for (String idItem : categorias.split(",")) {

			categoria = new Categoria();

			categoria.setId(Long.parseLong(idItem));

			listRetorno.addAll(new ItemDAO().getAll(categoria));

		}

		this.configureReturnObjects(listRetorno, fields);

		return listRetorno;
	}

}
