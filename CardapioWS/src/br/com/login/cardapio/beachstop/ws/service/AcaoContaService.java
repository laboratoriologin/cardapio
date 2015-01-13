package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.model.Acao;
import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
@Path("/acoes_contas")
public class AcaoContaService extends RestService<AcaoConta> {

	@Override
	public void initDAO() {
		this.restDAO = new AcaoContaDAO();
	}
	
	@POST
	@Path("/fercharconta")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta insertFecharConta(@Form AcaoConta form) throws ApplicationException {

		form.setAcao(new Acao(Constantes.Acoes.PedirConta.toString()));
		form.setUsuario(new Usuario());

		this.insert(form);
		
		return new AcaoConta();
	}
	
	@POST
	@Path("/chamargarcom")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta insertChamarGarcom(@Form AcaoConta form) throws ApplicationException {

		form.setAcao(new Acao(Constantes.Acoes.ChamarGarcom.toString()));
		form.setUsuario(new Usuario());
		
		this.insert(form);

		return new AcaoConta();
	}

}
