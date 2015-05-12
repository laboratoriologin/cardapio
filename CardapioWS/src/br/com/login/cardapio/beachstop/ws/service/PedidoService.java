package br.com.login.cardapio.beachstop.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.dao.AcaoContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.AreasDAO;
import br.com.login.cardapio.beachstop.ws.dao.ContaDAO;
import br.com.login.cardapio.beachstop.ws.dao.ItemDAO;
import br.com.login.cardapio.beachstop.ws.dao.KitDAO;
import br.com.login.cardapio.beachstop.ws.dao.LogDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoDAO;
import br.com.login.cardapio.beachstop.ws.dao.PedidoSubItemDAO;
import br.com.login.cardapio.beachstop.ws.dao.StatusDAO;
import br.com.login.cardapio.beachstop.ws.dao.SubItemDAO;
import br.com.login.cardapio.beachstop.ws.dao.UsuarioDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.Acao;
import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.login.cardapio.beachstop.ws.model.Kit;
import br.com.login.cardapio.beachstop.ws.model.KitSubItem;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.model.SubItem;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

@Path("/pedidos")
public class PedidoService extends RestService<Pedido> {

	@Override
	public void initDAO() {
		this.restDAO = new PedidoDAO();
	}

	@GET
	@Path("pedidosnaoconcluido/")
	@Produces("application/json; charset=UTF-8")
	public List<Pedido> get() {

		List<Status> listStatus = new ArrayList<Status>();
		listStatus.add(new Status(3l));
		listStatus.add(new Status(4l));

		List<Pedido> pedidos = new PedidoDAO().getAllByOuterJoinStatus(listStatus);
		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();

		for (Pedido pedido : pedidos) {
			pedido.setSubItens(pedidoSubItemDAO.getAllOuterStatus(pedido, new Status(3l)));
		}

		return pedidos;
	}

	@GET
	@Path("pedidohistorico/{conta_id}")
	@Produces("application/json; charset=UTF-8")
	public List<Pedido> getPedidoHistorico(@PathParam("conta_id") String contaId) {
		List<Pedido> pedidos = new PedidoDAO().getAll(contaId);
		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();
		LogDAO log = new LogDAO();

		Long qtd;
		for (Pedido pedido : pedidos) {
			pedido.setSubItens(pedidoSubItemDAO.getAllByPedidoHasStatus(pedido));

			pedido.setHorarioSolicitacao(log.getHorarioSolicitacaoPedido(pedido).getStrHorario());

			qtd = pedido.getSubItens().stream().filter(u -> u.getStatus().getId().equals(Constantes.StatusPedido.CANCELADO)).count();

			if (qtd.equals(new Long(pedido.getSubItens().size()))) {
				pedido.setCancelado(true);
			} else {
				pedido.setCancelado(false);
			}
		}
		return pedidos;
	}

	@GET
	@Path("pedidosafazer/{id}")
	@Produces("application/json; charset=UTF-8")
	public List<Pedido> getPedidoCozinha(@PathParam("id") Long usuarioId) {

		Usuario usuario = new UsuarioDAO().get(usuarioId);
		Area area = new AreasDAO().getByGrupoUsuario(usuario.getGrupoUsuario().getId());

		List<Pedido> pedidos = new PedidoDAO().getAllByAreaAndStatus(area, new Status(2l));
		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();

		for (Pedido pedido : pedidos) {
			pedido.setSubItens(pedidoSubItemDAO.getAllPedidoStatusArea(pedido, new Status(2l), area));
		}

		return pedidos;
	}
	
	@GET
	@Path("pedidosafazer/area/{area_id}")
	@Produces("application/json; charset=UTF-8")
	public List<Pedido> getPedidoAFazerByArea(@PathParam("area_id") String areaId) {

		Area area = new Area(areaId); 

		List<Pedido> pedidos = new PedidoDAO().getAllByAreaAndStatus(area, new Status(2l));
		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();

		for (Pedido pedido : pedidos) {
			pedido.setSubItens(pedidoSubItemDAO.getAllPedidoStatusArea(pedido, new Status(2l), area));
		}

		return pedidos;
	}

	@Override
	@GET
	@Path("/{id}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public Pedido get(@PathParam("id") Long id, @PathParam("fields") String fields) {

		Pedido pedido = restDAO.get(id);

		if (pedido != null) {

			LogDAO logDAO = new LogDAO();

			pedido.setSubItens(new PedidoSubItemDAO().getAll(pedido));

			for (PedidoSubItem subItem : pedido.getSubItens()) {

				subItem.setLogs(logDAO.getAll(subItem));

			}

			this.configureReturnObject(pedido, fields);

		}

		return pedido;

	}

	@Override
	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public Pedido insert(@Form Pedido form) throws ApplicationException {

		PedidoSubItem pedidoSubItemKit;
		List<PedidoSubItem> pedidoSubItemFinal = new ArrayList<PedidoSubItem>();
		SubItem subItem;
		Kit kit;
		KitDAO kitDAO = new KitDAO();
		SubItemDAO subItemDAO = new SubItemDAO();

		for (PedidoSubItem pedidoSubItem : form.getSubItens()) {
			if (pedidoSubItem.getKit() != null) {
				kit = kitDAO.get(pedidoSubItem.getKit().getId());

				for (KitSubItem kitSubItem : kit.getKitSubItens()) {
					subItem = subItemDAO.get(kitSubItem.getSubItem().getId());

					pedidoSubItemKit = new PedidoSubItem();
					pedidoSubItemKit.setKit(kit);
					pedidoSubItemKit.setPedido(form);
					pedidoSubItemKit.setQuantidade(Integer.valueOf(kitSubItem.getQtd().toString()));
					pedidoSubItemKit.setSubItem(subItem);

					pedidoSubItemFinal.add(pedidoSubItemKit);
				}
			} else {
				pedidoSubItemFinal.add(pedidoSubItem);
			}
		}

		form.setSubItens(pedidoSubItemFinal);
		final boolean pedidoPeloGarcom = form.getUsuario() != null;
		Status status = new Status();

		if (pedidoPeloGarcom)
			status.setId(Constantes.StatusPedido.PENDENTE_ENTREGA);
		else
			status.setId(Constantes.StatusPedido.PENDENTE_APROVACAO);

		for (PedidoSubItem item : form.getSubItens()) {

			if (item.getFinished() != null && item.isFinished())
				item.setStatus(new Status(Constantes.StatusPedido.ENTREGUE));
			else
				item.setStatus(status);

		}

		// quando não tem o usuário, significa que o cliente realizou o pedido e
		// tem que buscar o id da conta pelo número da mesa
		// Caso tenha usuario já tem que passar o numero da mesa
		if (form.getUsuario() == null) {
			form.setUsuario(new Usuario());
		} else {
			form.setConta(new ContaDAO().getByMesa(form.getNumero()));
		}

		super.insert(form);
		this.gerarLog(form);
		this.gerarAcaoConta(form);
		return new Pedido();
	}

	@Override
	@PUT
	@Path("/{id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido update(@Form Pedido form, @PathParam("id") Long id) throws ApplicationException {

		form.setId(id);

		Pedido pedidoAnterior = new PedidoDAO().get(id);
		Pedido pedidoCancelado = new PedidoDAO().get(id);
		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();
		SubItem subItem;

		if (pedidoCancelado.getSubItens().removeAll(form.getSubItens())) {
			pedidoCancelado.setUsuario(form.getUsuario());
			gerarLog(pedidoCancelado, new Status(Constantes.StatusPedido.CANCELADO));
		}

		try {
			for (PedidoSubItem pedidoSubItem : form.getSubItens()) {
				if (pedidoAnterior.getSubItens().contains(pedidoSubItem))
					pedidoSubItemDAO.update(pedidoSubItem);
				else {

					subItem = new SubItemDAO().get(pedidoSubItem.getSubItem().getId());

					pedidoSubItem.setPedido(form);
					pedidoSubItem.setSubItem(subItem);
					pedidoSubItem.setValorUnitario(subItem.getValor());
					pedidoSubItemDAO.insert(pedidoSubItem);
				}
			}
			gerarLog(form, new Status(Constantes.StatusPedido.PENDENTE_ENTREGA));
			new AcaoContaDAO().finalizarAcaoConta(form.getUsuario().getId(), form.getAcaoContaId());

		} catch (TSApplicationException e) {
			e.printStackTrace();
		}

		return new Pedido();
	}

	@PUT
	@Path("/aprovar/{id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido aprovar(@Form Pedido form, @PathParam("id") Long id) throws ApplicationException {

		try {
			PedidoDAO pedidoDAO = new PedidoDAO();
			Pedido pedido = pedidoDAO.get(id);
			// se ninguem fez nenhuma operacao antes, pode alterar o registro.
			// Apenas um controle de transação
			if (TSUtil.isEmpty(pedido.getUsuario().getId())) {
				form.setId(id);
				pedidoDAO.aprovar(form);
				form.setSubItens(new PedidoSubItemDAO().getAll(form));
				this.gerarLog(form, new Status(Constantes.StatusPedido.PENDENTE_ENTREGA));
			}
			return form;
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/pedidopronto/usuario/{id}/pedidosubitem/{pedido_sub_item_id}/pedido/{pedido_id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido pedidoPronto(@PathParam("id") String usuarioId, @PathParam("pedido_sub_item_id") String pedidoSubItemId, @PathParam("pedido_id") String pedidoId) throws ApplicationException {
		try {

			Pedido pedido = new Pedido(pedidoId);
			pedido.setUsuario(new Usuario(usuarioId));
			pedido.setSubItens(new ArrayList<PedidoSubItem>());
			PedidoSubItem pedidoSubItem;
			for (String strPedidoSubItemId : pedidoSubItemId.split(",")) {
				pedidoSubItem = new PedidoSubItem(strPedidoSubItemId);
				pedido.getSubItens().add(pedidoSubItem);
			}

			gerarLog(pedido, new Status(3l));

			return new Pedido();

		} catch (TSSystemException ex) {
			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/cancelar/{id}")
	@Produces("application/json; charset=UTF-8")
	public Pedido cancelarADM(@Form Pedido pedido, @PathParam("id") Long id) throws ApplicationException {
		try {
			new PedidoDAO().cancelar(pedido, pedido.getUsuario());
		} catch (TSApplicationException ex) {
			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);
		} catch (TSSystemException ex) {
			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);
		}
		return pedido;
	}

	private void gerarAcaoConta(Pedido pedido) {

		AcaoConta acaoConta = new AcaoConta();
		acaoConta.setConta(pedido.getConta());
		acaoConta.setAcao(new Acao(Constantes.Acoes.NovoPedido.toString()));

		if (pedido.getUsuario() == null)
			acaoConta.setUsuario(new Usuario());
		else
			acaoConta.setUsuario(pedido.getUsuario());

		acaoConta.setPedido(pedido);

		try {
			new AcaoContaDAO().insert(acaoConta);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void gerarLog(Pedido pedido, Status status) {
		try {
			for (PedidoSubItem subItem : pedido.getSubItens()) {
				subItem.setStatus(status);
			}
			new LogDAO().insert(pedido);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void gerarLog(Pedido pedido) {
		try {
			new LogDAO().insert(pedido);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
