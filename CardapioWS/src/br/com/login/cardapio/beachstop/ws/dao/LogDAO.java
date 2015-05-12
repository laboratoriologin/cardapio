package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Area;
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

	public Log getHorarioSolicitacaoPedido(Pedido pedido) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("logdao.gethorariosolicitacaopedido", pedido.getId());
		return (Log) broker.getObjectBean(Log.class, "strHorario");
	}

	public List<Log> getAll(PedidoSubItem pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("logdao.findallbypedido", pedido.getId());

		return broker.getCollectionBean(Log.class, "id", "usuario.id", "usuario.nome", "status.id", "status.descricao", "horario");

	}

	public List<Log> getAll(Status status, String filtroMinuto) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder(" SELECT C.NUMERO, I.NOME, SI.NOME ");
		sql.append(" FROM LOGS AS L ").append(" INNER JOIN PEDIDOS_SUB_ITENS AS PSI ON PSI.ID = L.PEDIDO_SUB_ITEM_ID ").append(" INNER JOIN SUB_ITENS AS SI ON SI.ID = PSI.SUB_ITEM_ID ").append(" INNER JOIN ITENS AS I ON I.ID = SI.ITEM_ID ").append(" INNER JOIN PEDIDOS AS P ON P.ID = PSI.PEDIDO_ID ").append(" INNER JOIN CONTAS AS C ON C.ID = P.CONTA_ID  ").append(" WHERE DATEDIFF(MINUTE, HORARIO, GETDATE()) <= ").append(filtroMinuto).append(" GROUP BY C.NUMERO, I.NOME, SI.NOME ").append("  HAVING MAX(L.STATUS_ID) = ").append(status.getId());

		broker.setSQL(sql.toString());

		List<Log> listLog = broker.getCollectionBean(Log.class, "pedidoSubItem.pedido.conta.numero", "pedidoSubItem.subItem.item.nome", "pedidoSubItem.subItem.nome");

		return listLog;
	}

	public List<Log> getAll(Status status, Area area, String qtdRow) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder(" SELECT TOP 10 L.ID,C.NUMERO, I.NOME, SI.NOME, PSI.QUANTIDADE ");
		sql.append(" FROM LOGS AS L ").append(" INNER JOIN PEDIDOS_SUB_ITENS AS PSI ON PSI.ID = L.PEDIDO_SUB_ITEM_ID ").append(" INNER JOIN SUB_ITENS AS SI ON SI.ID = PSI.SUB_ITEM_ID ").append(" INNER JOIN ITENS AS I ON I.ID = SI.ITEM_ID ").append(" INNER JOIN CATEGORIAS AS CA ON CA.ID = I.CATEGORIA_ID ").append(" INNER JOIN PEDIDOS AS P ON P.ID = PSI.PEDIDO_ID ").append(" INNER JOIN CONTAS AS C ON C.ID = P.CONTA_ID ").append(" WHERE CA.AREA_ID = ").append(area.getId()).append(" GROUP BY L.ID, C.NUMERO, I.NOME, SI.NOME, PSI.QUANTIDADE ").append("  HAVING MAX(L.STATUS_ID) = ").append(status.getId()).append(" ORDER BY L.ID DESC ");

		broker.setSQL(sql.toString());

		List<Log> listLog = broker.getCollectionBean(Log.class, "id", "pedidoSubItem.pedido.conta.numero", "pedidoSubItem.subItem.item.nome", "pedidoSubItem.subItem.nome", "pedidoSubItem.quantidade");

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
