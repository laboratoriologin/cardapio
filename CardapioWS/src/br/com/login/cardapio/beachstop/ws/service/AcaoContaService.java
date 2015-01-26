package br.com.login.cardapio.beachstop.ws.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.model.Acao;
import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.model.UsuarioSetor;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioSetorDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/acoes_contas")
public class AcaoContaService extends RestService<AcaoConta> {

	@Override
	public void initDAO() {
		this.restDAO = new AcaoContaDAO();
	}
	
	@GET
	@Path("/empresa/{keymobile}/usuario/{usuarioId}")
	@Produces("application/json; charset=UTF-8")
	public List<AcaoConta> getlAllOpen(@PathParam("keymobile")String keymobile, @PathParam("usuarioId") String usuarioId) {
		//Se a variavel setorId for igual a zero, irá buscar todas as ações de todos os setores

		List<UsuarioSetor> usuarioSetores = new UsuarioSetorDAO().getAllByUsuario(new Usuario(usuarioId));
		String setorId;
		
		if(usuarioSetores.size() == 0){
			setorId = "0";
		}else{
			setorId = usuarioSetores.stream().map(UsuarioSetor::toString).collect(Collectors.joining(", "));
		}
		
		return new AcaoContaDAO().getAllOpen(setorId);
	}
	
	@POST
	@Path("/fercharconta")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta insertFecharConta(@Form AcaoConta form) throws ApplicationException {
		try {
			form.setAcao(new Acao(Constantes.Acoes.PedirConta.toString()));
			form.setUsuario(new Usuario());
			form.setPedido(new Pedido());
			return this.insert(form);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	@POST
	@Path("/chamargarcom")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta insertChamarGarcom(@Form AcaoConta form) throws ApplicationException {
		try {
			form.setAcao(new Acao(Constantes.Acoes.ChamarGarcom.toString()));
			form.setUsuario(new Usuario());	
			form.setPedido(new Pedido());
			return this.insert(form);			
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@PUT
	@Path("/{id}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta update(@Form AcaoConta form, @PathParam("id") Long id) throws ApplicationException {

		try {
			new AcaoContaDAO().updateResolverAcao(form);
			
			if(Constantes.Acoes.PedirConta.equals(form.getAcao().getId())){
				new ContaDAO().fecharConta(form.getConta());
				//TODO: incluir rotina de aviso ao adm, que a conta foi fechar para imprimir a comanda de verificação e nota fiscal
			}
			
			return form;
			
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
