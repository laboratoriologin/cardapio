package br.com.login.cardapio.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.ws.dao.CategoriaCardapioDAO;
import br.com.login.cardapio.ws.model.CategoriaCardapio;

@Path("/categorias_cardapio")
public class CategoriaCardapioService extends RestService<CategoriaCardapio> {

	@Override
	public void initDAO() {
		this.restDAO = new CategoriaCardapioDAO();
	}

	@GET
	@Path("/empresa/{empresa}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public List<CategoriaCardapio> getAll(@PathParam(value = "empresa") String empresa, @PathParam(value = "fields") String fields) {

		CategoriaCardapioDAO dao = new CategoriaCardapioDAO();

		List<CategoriaCardapio> categorias = dao.getAll(empresa);

		this.configureReturnObjects(categorias, fields);

		return categorias;

	}
}
