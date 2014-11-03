package br.com.login.cardapio.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.ws.dao.ItemDAO;
import br.com.login.cardapio.ws.model.CategoriaCardapio;
import br.com.login.cardapio.ws.model.Empresa;
import br.com.login.cardapio.ws.model.Item;

@Path("/itens")
public class ItemService extends RestService<Item> {

	@Override
	public void initDAO() {
		this.restDAO = new ItemDAO();
	}

	@GET
	@Path("/keymobile/{empresa}/categorias/{categorias}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public List<Item> getItensByCategoria(@PathParam(value = "empresa") String empresa, @PathParam(value = "categorias") String categorias, @PathParam(value = "fields") String fields) {

		List<Item> listRetorno = new ArrayList<Item>();
		CategoriaCardapio categoriaCardapio;

		Empresa argEmpresa = new Empresa();
		argEmpresa.setKeyMobile(empresa);

		for (String idItem : categorias.split(",")) {

			categoriaCardapio = new CategoriaCardapio();

			categoriaCardapio.setId(Long.parseLong(idItem));

			listRetorno.addAll(new ItemDAO().getAll(argEmpresa, categoriaCardapio));

		}

		this.configureReturnObjects(listRetorno, fields);

		return listRetorno;
	}

}
