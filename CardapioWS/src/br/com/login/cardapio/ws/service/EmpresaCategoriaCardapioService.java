package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.EmpresaCategoriaCardapio;
import br.com.login.cardapio.ws.dao.EmpresaCategoriaCardapioDAO;
@Path("/empresas_categorias_cardapios")
public class EmpresaCategoriaCardapioService extends RestService<EmpresaCategoriaCardapio> {

	@Override
	public void initDAO() {
		this.restDAO = new EmpresaCategoriaCardapioDAO();
	}

}
