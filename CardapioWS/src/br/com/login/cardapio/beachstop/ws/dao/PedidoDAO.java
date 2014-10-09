package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PedidoDAO  implements RestDAO<Pedido> {

	@Override
	public Pedido get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.get", id);

		return (Pedido) broker.getObjectBean(Pedido.class, "conta.id", "id", "observacao");

	}

	@Override
	public List<Pedido> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.findall");

		return broker.getCollectionBean(Pedido.class, "conta.id", "id", "observacao");

	}

	@Override
	public Pedido insert(Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pedidos "));

		broker.setPropertySQL("pedidodao.insert",model.getConta().getId(), model.getObservacao());

		broker.execute();

		return model;

	}

	@Override
	public Pedido update(final Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.update", model.getConta().getId(), model.getObservacao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.delete", id);

		broker.execute();

	}

}
