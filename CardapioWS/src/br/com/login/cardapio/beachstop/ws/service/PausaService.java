package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.dao.PausaDAO;
import br.com.login.cardapio.beachstop.ws.model.Pausa;

@Path("/pausas")
public class PausaService extends RestService<Pausa> {

	@Override
	public void initDAO() {
		this.restDAO = new PausaDAO();
	}

	@GET
	@Path("getempausa/")
	@Produces("application/json; charset=UTF-8")
	public List<Pausa> getEmPausa() {
		return new PausaDAO().getEmPausa();
	}
}
