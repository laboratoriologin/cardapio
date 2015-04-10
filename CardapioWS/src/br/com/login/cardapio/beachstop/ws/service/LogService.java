package br.com.login.cardapio.beachstop.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Log;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.LogDAO;

@Path("/logs")
public class LogService extends RestService<Log> {

	@Override
	public void initDAO() {
		this.restDAO = new LogDAO();
	}

	@GET
	@Path("/status/{statusId}")
	@Produces("application/json; charset=UTF-8")
	public List<Log> getAllByStatus(@PathParam("statusId") String statusId) {
		Status status = new Status(statusId);
		return new LogDAO().getAll(status, "10000000000000");
	}
}
