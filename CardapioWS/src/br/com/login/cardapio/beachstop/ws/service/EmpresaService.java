package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Empresa;
import br.com.login.cardapio.beachstop.ws.dao.EmpresaDAO;
@Path("/empresas")
public class EmpresaService extends RestService<Empresa> {

	@Override
	public void initDAO() {
		this.restDAO = new EmpresaDAO();
	}

}
