package br.com.login.cardapio.beachstop.ws.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.apache.commons.beanutils.BeanUtils;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.PagamentoDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoDAO;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioSetorDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.Acao;
import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Pagamento;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.model.SubItem;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.model.UsuarioSetor;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

@Path("/acoes_contas")
public class AcaoContaService extends RestService<AcaoConta> {

	@Override
	public void initDAO() {
		this.restDAO = new AcaoContaDAO();
	}

	@GET
	@Path("/getsolicitacaofecharconta")
	@Produces("application/json; charset=UTF-8")
	public List<AcaoConta> getSolicitacaoFecharConta() {
		return new AcaoContaDAO().getSolicitacaoFecharConta();
	}

	@GET
	@Path("/acao/{acaoId}")
	@Produces("application/json; charset=UTF-8")
	public List<AcaoConta> getAlllOpenByTipoAcao(@PathParam("acaoId") String acaoId) {

		if (acaoId != null && !acaoId.isEmpty())
			return new AcaoContaDAO().getOpenByTipoConta(acaoId);
		else
			return new ArrayList<AcaoConta>();
	}

	@GET
	@Path("/empresa/{keymobile}/usuario/{usuarioId}")
	@Produces("application/json; charset=UTF-8")
	public List<AcaoConta> getlAllOpen(@PathParam("keymobile") String keymobile, @PathParam("usuarioId") String usuarioId) {
		List<UsuarioSetor> usuarioSetores = new UsuarioSetorDAO().getAllByUsuario(new Usuario(usuarioId));
		String setorId;

		// Se a variavel setorId for igual a zero, irá buscar todas as ações de
		// todos os setores
		if (usuarioSetores.size() == 0) {
			setorId = "0";
		} else {
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

			if (form.getUsuario() == null)
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

	@POST
	@Path("/autorizacao/{numero}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta insertAutorizacao(@Form AcaoConta form, @PathParam("numero") Long numero) throws ApplicationException {
		try {
			form.setConta(new ContaDAO().getByMesa(numero));
			form.setNumero(form.getConta().getNumero().toString());
			form.setAcao(new Acao(Constantes.Acoes.AutorizacaoAssociacao.toString()));
			form.setUsuario(new Usuario());
			form.setPedido(new Pedido());
			form.setAutorizacao(false);
			return this.insert(form);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/isautorizado/{acaoContaId}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta isAutorizacao(@PathParam("acaoContaId") Long acaoContaId) throws ApplicationException {

		AcaoConta acaoConta = new AcaoContaDAO().get(acaoContaId);

		if (acaoConta == null) {
			throw new ApplicationException("", Response.SC_UNAUTHORIZED);
		} else {
			if (acaoConta.getUsuario().getId() == null) {
				acaoConta = new AcaoConta();
				acaoConta.setAutorizacao(false);
			} else {
				acaoConta = new AcaoConta();
				acaoConta.setAutorizacao(true);
			}
		}

		return acaoConta;
	}

	@Override
	@PUT
	@Path("/{id}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta update(@Form AcaoConta form, @PathParam("id") Long id) throws ApplicationException {

		try {
			new AcaoContaDAO().updateResolverAcao(form);

			if (Constantes.Acoes.PedirConta.equals(form.getAcao().getId())) {
				new ContaDAO().fecharConta(form.getConta());
			}

			return form;

		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/cancelarpedido/{id}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta cancelarPedido(@Form AcaoConta form, @PathParam("id") Long id) throws ApplicationException {

		try {
			new AcaoContaDAO().updateResolverAcao(form);
			new PedidoDAO().cancelar(form.getPedido(), form.getUsuario());
			return form;
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/reabrirconta/{id}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta reabrirConta(@Form AcaoConta form, @PathParam("id") Long id) throws ApplicationException {

		try {

			AcaoContaDAO acaoContaDAO = new AcaoContaDAO();
			acaoContaDAO.changeIdFechadoTOReabrir(id);
			AcaoConta acaoConta = acaoContaDAO.get(id);
			new ContaDAO().reabrirConta(acaoConta.getConta());

			return acaoConta;

		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/reabrirconta/conta/{id}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta reabrirContaByConta(@Form AcaoConta form, @PathParam("id") String id) throws ApplicationException {

		try {
			Conta conta = new Conta(id);
			new ContaDAO().reabrirConta(conta);
			new AcaoContaDAO().changeIdFechadoTOReabrir(conta);

			return new AcaoConta();

		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/aprovarpedido/{id}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta aprovarPedido(@Form AcaoConta form, @PathParam("id") Long id) throws ApplicationException {

		try {
			new AcaoContaDAO().updateResolverAcao(form);
			new PedidoDAO().aprovar(form.getPedido(), form.getUsuario());
			return form;
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Path("/mudarmesa/{mesa_para}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta mudarMesa(@Form AcaoConta form, @PathParam("mesa_para") Integer mesaPara) throws ApplicationException {

		try {
			form.setAcao(new Acao(Constantes.Acoes.MudarMesa.toString()));
			form.setPedido(new Pedido());
			form = new AcaoContaDAO().insert(form);

			form.getConta().setNumero(mesaPara);

			new ContaDAO().updateMesa(form.getConta());
			return form;
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Path("/juntarmesa/origem/{mesas_origem}/destino/{mesa_destino}/usuario/{usuario}")
	@Produces("application/json; charset=UTF-8")
	public AcaoConta juntarMesa(@Form AcaoConta form, @PathParam("mesas_origem") String mesasOrigem, @PathParam("mesa_destino") String mesaDestino, @PathParam("usuario") String usuario) {

		PedidoService pedidoService = new PedidoService();
		List<Conta> contas = new ArrayList<Conta>();
		List<Pagamento> listPagamento = new ArrayList<Pagamento>();
		ContaDAO contaDAO = new ContaDAO();
		PagamentoDAO pagamentoDAO = new PagamentoDAO();
		Conta conta;
		Conta contaDestino;
		AcaoConta acaoConta;
		Pedido pedido;

		// TODO: Verificar se a mesa de destino está ocupa, se não mandar msg
		// solicitando a troca de mesa

		if (!"".equals(mesasOrigem)) {

			contaDestino = contaDAO.getByNumeroAnalitico(Long.parseLong(mesaDestino));

			if (contaDestino == null) {
				contaDestino = new Conta();
				contaDestino.setNumero(Integer.parseInt(mesaDestino));
				contaDestino.setTipoConta(false);
				contaDestino.setQtdPessoa(1);
				contaDestino.setDataAbertura(new Date(System.currentTimeMillis()));
				contaDestino.setPedidos(new ArrayList<Pedido>());

				try {
					contaDestino = contaDAO.insert(contaDestino);
				} catch (TSApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			List<String> mesas = Arrays.asList(mesasOrigem.split(","));
			for (String mesa : mesas) {
				conta = contaDAO.getByNumeroAnalitico(Long.parseLong(mesa));

				if (conta != null) {
					contas.add(conta);

					for (Pedido pedidoAux : conta.getPedidos()) {

						try {
							pedido = (Pedido) BeanUtils.cloneBean(pedidoAux);
							pedido.setSubItens(new ArrayList<PedidoSubItem>());
							for (PedidoSubItem pedidoSubItemAux : pedidoAux.getSubItens()) {
								pedido.getSubItens().add((PedidoSubItem) BeanUtils.cloneBean(pedidoSubItemAux));
							}

							pedido.setConta(contaDestino);
							pedido.setUsuario(new Usuario(usuario));
							contaDestino.getPedidos().add(pedido);

							pedidoService.inserPedidoJoinMesa(pedido);

							pedidoAux.setUsuario(new Usuario(usuario));
							pedidoService.gerarLog(pedidoAux, new Status(Constantes.StatusPedido.TRANSFERIDO));

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					listPagamento = new PagamentoDAO().getAllByConta(conta);
					
					if (listPagamento.size() != 0) {
						for(Pagamento pagamento : listPagamento){
							pagamento.setConta(contaDestino);
							try {
								pagamentoDAO.insert(pagamento);
							} catch (TSApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					// TODO: Fechar todas as contas destinos
					try {
						contaDAO.fecharConta(conta);
					} catch (TSApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					acaoConta = new AcaoConta();
					acaoConta.setAcao(new Acao(Constantes.Acoes.JuntarMesa.toString()));
					acaoConta.setPedido(new Pedido());
					acaoConta.setUsuario(new Usuario(usuario));
					acaoConta.setConta(conta);
					acaoConta.setContaDestino(contaDestino);

					try {
						this.insert(acaoConta);
					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return new AcaoConta();
	}
}
