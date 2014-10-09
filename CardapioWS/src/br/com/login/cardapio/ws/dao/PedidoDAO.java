package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Conta;
import br.com.login.cardapio.ws.model.Pedido;
import br.com.login.cardapio.ws.model.PedidoSubItem;
import br.com.login.cardapio.ws.model.Status;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PedidoDAO implements RestDAO<Pedido> {

	@Override
	public Pedido get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.get", id);

		return (Pedido) broker.getObjectBean(Pedido.class, "horario", "id", "conta.id", "observacao", "usuario.id");

	}

	@Override
	public List<Pedido> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.findall");

		return broker.getCollectionBean(Pedido.class, "horario", "id", "conta.id", "observacao");

	}

	public List<Pedido> getAll(Conta conta) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.findallbyconta", conta.getId());

		List<Pedido> listPedido = broker.getCollectionBean(Pedido.class, "horario", "id", "conta.id", "observacao");

		if (listPedido != null) {

			for (Pedido pedido : listPedido) {

				pedido.setListPedidoSubItem(new PedidoSubItemDAO().getAllGrouped(pedido));

			}

		}

		return listPedido;

	}

	public List<Pedido> getAllPendente(Conta conta) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.findallpendentebyconta", conta.getId());

		List<Pedido> listPedido = broker.getCollectionBean(Pedido.class, "horario", "id", "conta.id", "observacao");

		if (listPedido != null) {
			for (Pedido pedido : listPedido) {
				pedido.setListPedidoSubItem(new PedidoSubItemDAO().getAllPendenteGrouped(pedido));
			}
		}

		return listPedido;

	}

	public List<Pedido> getAllNaoEntregueCancelado(Conta conta, Boolean booLog) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.findallnaoentreguecanceladobyconta", conta.getId());

		List<Pedido> listPedido = broker.getCollectionBean(Pedido.class, "horario", "id", "conta.id", "observacao");

		if (listPedido != null) {
			for (Pedido pedido : listPedido) {
				pedido.setListPedidoSubItem(new PedidoSubItemDAO().getAllNaoEntregueCanceladoGrouped(pedido, booLog));
			}
		}

		return listPedido;

	}

	public void associarUsuario(Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.alterarresponsavel", model.getUsuario().getId(), model.getId());

		broker.execute();

	}

	@Override
	public Pedido insert(Pedido model) throws TSApplicationException {

		ContaDAO contaDAO = new ContaDAO();
		Conta conta = contaDAO.get(model.getConta().getId());

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		model.setId(broker.getSequenceNextValue("dbo.pedidos"));

		broker.setPropertySQL("pedidodao.insert", model.getObservacao(), model.getConta().getId());

		broker.execute();

		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();

		for (PedidoSubItem item : model.getListPedidoSubItem()) {

			item.setPedido(model.getId());

			if (model.getUsuario() == null) {

				item.setStatus(new Status(Status.PENDENTE_APROVACAO));

			} else {

				broker.setPropertySQL("pedidodao.alterarresponsavel", model.getUsuario().getId(), model.getId());

				broker.execute();

				item.setStatus(new Status(Status.PENDENTE_ENTREGA));

			}

			item = pedidoSubItemDAO.insert(item, broker);
		}

		conta.setPedidoSubItem(model.getListPedidoSubItem());

		broker.endTransaction();

		contaDAO.updateValorTotal(conta);

		return model;

	}

	@Override
	public Pedido update(final Pedido model) throws TSApplicationException {

		ContaDAO contaDAO = new ContaDAO();

		Conta conta = contaDAO.get(model.getConta().getId());

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		broker.setPropertySQL("pedidodao.updateobs", model.getObservacao(), model.getId());

		broker.execute();

		broker.setPropertySQL("pedidodao.alterarresponsavel", model.getUsuario().getId(), model.getId());

		broker.execute();

		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();

		pedidoSubItemDAO.canelarByPedido(model.getId(), broker);

		for (PedidoSubItem item : model.getListPedidoSubItem()) {

			item.setStatus(new Status(Status.PENDENTE_ENTREGA));

			if (TSUtil.isEmpty(item.getId())) {

				item.setPedido(model.getId());

				item = pedidoSubItemDAO.insert(item, broker);

			} else {

				item = pedidoSubItemDAO.update(item, broker);

			}

		}

		conta.setPedidoSubItem(model.getListPedidoSubItem());

		broker.endTransaction();

		contaDAO.updateValorTotal(conta);

		return model;

	}

	public void aprovar(final Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		broker.setPropertySQL("pedidodao.alterarresponsavel", model.getUsuario().getId(), model.getId());

		broker.execute();

		broker.setPropertySQL("pedidodao.aprovar", model.getId());

		broker.execute();

		broker.endTransaction();

	}

	public void cancelar(Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		broker.setPropertySQL("pedidodao.alterarresponsavel", model.getUsuario().getId(), model.getId());

		broker.execute();

		broker.setPropertySQL("pedidodao.cancelar", model.getId());

		broker.execute();

		broker.endTransaction();

		broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		Pedido pedido = get(model.getId());
		ContaDAO contaDAO = new ContaDAO();
		Conta conta = contaDAO.get(pedido.getConta().getId());

		broker.endTransaction();

		contaDAO.updateValorTotal(conta);
	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.delete", id);

		broker.execute();

	}

}
