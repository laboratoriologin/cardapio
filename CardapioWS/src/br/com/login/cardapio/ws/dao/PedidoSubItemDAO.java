package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Conta;
import br.com.login.cardapio.ws.model.Pedido;
import br.com.login.cardapio.ws.model.PedidoSubItem;
import br.com.login.cardapio.ws.model.Status;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class PedidoSubItemDAO implements RestDAO<PedidoSubItem> {

	@Override
	public PedidoSubItem get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.get", id);

		return (PedidoSubItem) broker.getObjectBean(PedidoSubItem.class, "subitem.item.id", "quantidade", "subitem.item.nome", "subitem.descricao", "subitem.valor", "valor");

	}

	@Override
	public List<PedidoSubItem> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.findall");

		return broker.getCollectionBean(PedidoSubItem.class, "subitem.item.id", "quantidade", "subitem.item.nome", "subitem.descricao", "subitem.valor", "valor");

	}

	public List<PedidoSubItem> getAll(Conta conta) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.findallbyconta", conta.getId());

		return broker.getCollectionBean(PedidoSubItem.class, "subitem.id", "subitem.item.id", "quantidade", "subitem.item.nome", "subitem.descricao", "subitem.valor", "valor", "status.id", "id");

	}

	public List<PedidoSubItem> getAllGrouped(Pedido pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.findallbypedidogrouped", pedido.getId());

		return broker.getCollectionBean(PedidoSubItem.class, "subitem.item.id", "quantidade", "subitem.item.nome", "subitem.descricao", "subitem.valor", "valor");

	}

	public List<PedidoSubItem> getAllPendenteGrouped(Pedido pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.findallbypedidopendentegrouped", pedido.getId());

		return broker.getCollectionBean(PedidoSubItem.class, "subitem.item.id", "quantidade", "subitem.item.nome", "subitem.descricao", "subitem.valor", "valor", "subitem.id", "id");

	}

	public List<PedidoSubItem> getAllNaoEntregueCanceladoGrouped(Pedido pedido, Boolean booLog) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.findallbypedidonaoentreguecanceladogrouped", pedido.getId());

		List<PedidoSubItem> pedidoSubItem = broker.getCollectionBean(PedidoSubItem.class, "subitem.item.id", "quantidade", "subitem.item.nome", "subitem.descricao", "subitem.valor", "valor", "subitem.id", "id");

		if (booLog) {
			LogDAO logDAO = new LogDAO();
			for (PedidoSubItem pedidoSubItem2 : pedidoSubItem) {
				pedidoSubItem2.setLogs(logDAO.getAll(pedidoSubItem2));
			}
		}

		return pedidoSubItem;

	}

	public List<PedidoSubItem> getAll(Pedido pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.findallbypedido", pedido.getId());

		return broker.getCollectionBean(PedidoSubItem.class, "id", "subitem.item.id", "subitem.item.nome", "subitem.descricao", "subitem.valor");

	}

	@Override
	public PedidoSubItem insert(PedidoSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens"));

		broker.setPropertySQL("itempedidodao.insert", model.getPedido(), model.getSubitem().getId(), model.getQuantidade(), model.getFlagCancelado());

		broker.execute();

		return model;

	}

	public PedidoSubItem insert(PedidoSubItem model, TSDataBaseBrokerIf broker) throws TSApplicationException {

		model.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens"));

		broker.setPropertySQL("itempedidodao.insert", model.getPedido(), model.getSubitem().getId(), model.getQuantidade(), model.getStatus().getId());

		broker.execute();

		return model;

	}

	@Override
	public PedidoSubItem update(final PedidoSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.update", model.getId(), model.getPedido(), model.getSubitem().getId(), model.getId());

		broker.execute();

		return model;

	}

	public PedidoSubItem update(final PedidoSubItem model, TSDataBaseBrokerIf broker) throws TSApplicationException {

		broker.setPropertySQL("itempedidodao.update", model.getQuantidade(), model.getStatus().getId(), model.getId());

		broker.execute();

		return model;

	}

	public void canelarByPedido(final Long idPedido, TSDataBaseBrokerIf broker) throws TSApplicationException {

		broker.setPropertySQL("itempedidodao.cancelar", Status.CANCELADO, idPedido);

		broker.execute();
	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itempedidodao.delete", id);

		broker.execute();

	}

}
