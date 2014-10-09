package br.com.login.cardapio.ws.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.ws.dao.ContaDAO;
import br.com.login.cardapio.ws.dao.EmpresaDAO;
import br.com.login.cardapio.ws.model.Empresa;

@Path("/empresas")
public class EmpresaService extends RestService<Empresa> {

	@Override
	public void initDAO() {
		this.restDAO = new EmpresaDAO();
	}

	@GET
	@Path("/keymobile/{empresa}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public Empresa getByKeyMobile(@PathParam(value = "empresa") String keymobile, @PathParam(value = "fields") String fields) {

		Empresa empresa = new EmpresaDAO().get(keymobile);

		this.configureReturnObject(empresa, fields);

		return empresa;
	}

	@GET
	@Path("getmesaocupadaandqtdmesa/{empresa}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public Empresa getMesaOcupadaAndQtdMesa(@PathParam(value = "empresa") String idEmpresa, @PathParam(value = "fields") String fields) {

		Empresa empresa = new EmpresaDAO().get(Long.valueOf(idEmpresa));

		empresa.setContas(new ContaDAO().getOcupadas(empresa.getId()));

		this.configureReturnObject(empresa, fields);

		return empresa;

	}

}
