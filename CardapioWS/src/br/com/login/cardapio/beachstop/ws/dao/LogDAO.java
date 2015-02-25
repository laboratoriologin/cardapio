package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Log;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class LogDAO implements RestDAO<Log> {

	@Override
	public Log get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.get", id);

		return (Log) broker.getObjectBean(Log.class, "horario", "id", "pedidoSubItem.id", "status.id", "usuario.id");

	}

	@Override
	public List<Log> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.findall");

		return broker.getCollectionBean(Log.class, "horario", "id", "pedidoSubItem.id", "status.id", "usuario.id");

	}

	public List<Log> getAll(PedidoSubItem pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.findallbypedido", pedido.getId());

		return broker.getCollectionBean(Log.class, "id", "usuario.id", "usuario.nome", "status.id", "status.descricao", "horario");

	}

	public List<Log> getAll(Status status) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.findallbystatus", status.getId());

		List<Log> listLog = broker.getCollectionBean(Log.class, "horario", "id", "pedidoSubItem.id", "status.id", "usuario.id");

		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		ContaDAO conta = new ContaDAO();
		SubItemDAO subItemDAO = new SubItemDAO();
		ItemDAO itemDAO = new ItemDAO();

		for (Log log : listLog) {
			log.setPedidoSubItem(pedidoSubItemDAO.get(log.getPedidoSubItem().getId()));
			log.getPedidoSubItem().setPedido(pedidoDAO.get(log.getPedidoSubItem().getPedido().getId()));
			log.getPedidoSubItem().getPedido().setConta(conta.get(log.getPedidoSubItem().getPedido().getConta().getId()));
			log.getPedidoSubItem().setSubItem(subItemDAO.get(log.getPedidoSubItem().getSubItem().getId()));
			log.getPedidoSubItem().getSubItem().setItem(itemDAO.get(log.getPedidoSubItem().getSubItem().getItem().getId()));
		}
		return listLog;
	}

	@Override
	public Log insert(Log model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.logs "));

		broker.setPropertySQL("logdao.insert", model.getPedidoSubItem().getId(), model.getStatus().getId(), model.getUsuario().getId());

		broker.execute();

		return model;

	}

	public Log insert(Log model, TSDataBaseBrokerIf broker) throws TSApplicationException {
		model.setId(broker.getSequenceNextValue("dbo.logs "));
		broker.setPropertySQL("logdao.insert", model.getPedidoSubItem().getId(), model.getStatus().getId(), model.getUsuario().getId());
		broker.execute();
		return model;
	}

	@Override
	public Log update(final Log model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.update", model.getHorario(), model.getPedidoSubItem().getId(), model.getStatus().getId(), model.getUsuario().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.delete", id);

		broker.execute();

	}

	public void insert(Pedido pedido) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		for (PedidoSubItem item : pedido.getSubItens()) {
			broker.setPropertySQL("logdao.insert", item.getId(), item.getStatus().getId(), pedido.getUsuario().getId());
			broker.execute();
		}

		broker.endTransaction();
	}
}
