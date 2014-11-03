package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Log;
import br.com.login.cardapio.ws.model.Pedido;
import br.com.login.cardapio.ws.model.PedidoSubItem;
import br.com.login.cardapio.ws.model.Status;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class LogDAO implements RestDAO<Log> {

	@Override
	public Log get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.get", id);

		return (Log) broker.getObjectBean(Log.class, "id", "usuario.id", "pedido.id", "status.id");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.findall");

		return broker.getCollectionBean(Log.class, "id", "usuario.id", "pedido.id", "status.id");

	}

	public List<Log> getAll(PedidoSubItem pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.findallbypedido", pedido.getId());

		return broker.getCollectionBean(Log.class, "id", "usuario.id", "usuario.nome", "status.id", "status.descricao", "horario");

	}

	@Override
	public Log insert(Log model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.logs"));

		broker.setPropertySQL("logdao.insert", model.getUsuario().getId(), model.getPedidoSubItem().getId(), model.getStatus().getId());

		broker.execute();

		return model;

	}

	public void insert(Pedido pedido) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		for (PedidoSubItem item : pedido.getListPedidoSubItem()) {

			broker.setPropertySQL("logdao.insert", pedido.getUsuario().getId(), item.getId(), item.getStatus().getId());

			broker.execute();

		}

		broker.endTransaction();

	}

	@Override
	public Log update(final Log model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.update", model.getId(), model.getUsuario().getId(), model.getPedidoSubItem().getId(), model.getStatus().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.delete", id);

		broker.execute();

	}

}