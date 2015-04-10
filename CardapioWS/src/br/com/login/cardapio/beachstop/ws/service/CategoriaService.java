package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.dao.CategoriaDAO;
import br.com.login.cardapio.beachstop.ws.model.Categoria;

@Path("/categorias")
public class CategoriaService extends RestService<Categoria> {

	@Override
	public void initDAO() {
		this.restDAO = new CategoriaDAO();
	}

	@GET
	@Path("/ativo")
	@Produces("application/json; charset=UTF-8")
	public List<Categoria> getAll() {

		List<Categoria> listCardapio;

		listCardapio = new CategoriaDAO().getAtivos();
				
		return listCardapio;

	}

}
