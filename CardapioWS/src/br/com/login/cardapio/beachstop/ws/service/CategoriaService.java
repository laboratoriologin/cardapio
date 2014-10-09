package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Categoria;
import br.com.login.cardapio.beachstop.ws.dao.CategoriaDAO;
@Path("/categorias")
public class CategoriaService extends RestService<Categoria> {

	@Override
	public void initDAO() {
		this.restDAO = new CategoriaDAO();
	}

}
