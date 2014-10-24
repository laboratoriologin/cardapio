package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PedidoSubItemDAO  implements RestDAO<PedidoSubItem> {

	@Override
	public PedidoSubItem get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.get", id);

		return (PedidoSubItem) broker.getObjectBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario");

	}

	@Override
	public List<PedidoSubItem> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.findall");

		return broker.getCollectionBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario");

	}

	@Override
	public PedidoSubItem insert(PedidoSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens "));

		broker.setPropertySQL("pedidosubitemdao.insert",model.getPedido().getId(), model.getQuantidade(), model.getSubItem().getId(), model.getValorUnitario());

		broker.execute();

		return model;

	}

	@Override
	public PedidoSubItem update(final PedidoSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.update", model.getPedido().getId(), model.getQuantidade(), model.getSubItem().getId(), model.getValorUnitario(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.delete", id);

		broker.execute();

	}

}
