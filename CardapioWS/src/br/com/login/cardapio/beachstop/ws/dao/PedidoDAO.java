package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;
import java.util.StringJoiner;

import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Log;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.model.SubItem;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PedidoDAO implements RestDAO<Pedido> {

	@Override
	public Pedido get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.get", id);

		Pedido pedido = (Pedido) broker.getObjectBean(Pedido.class, "conta.id", "id", "observacao");

		pedido.setSubItens(new PedidoSubItemDAO().getAll(pedido));

		return pedido;
	}

	@Override
	public List<Pedido> getAll() {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidodao.findall");
		return broker.getCollectionBean(Pedido.class, "conta.id", "id", "observacao");
	}

	public List<Pedido> getAll(String contaId) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidodao.findallbyconta", contaId);
		return broker.getCollectionBean(Pedido.class, "conta.id", "id", "observacao");
	}

	public List<Pedido> getAllByOuterJoinStatus(List<Status> status) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringJoiner joiner = new StringJoiner(",");
		status.forEach((obj) -> joiner.add(obj.getId().toString()));

		StringBuilder sql = new StringBuilder(" SELECT P.ID, C.NUMERO  ");
         							 sql.append(" FROM PEDIDOS AS P ")
     			                  .append(" INNER JOIN PEDIDOS_SUB_ITENS AS PSI ON PSI.PEDIDO_ID = P.ID ")
     			                  .append(" INNER JOIN LOGS AS L ON L.PEDIDO_SUB_ITEM_ID = PSI.ID ")
     			                  .append(" INNER JOIN CONTAS AS C ON P.CONTA_ID = C.ID ")
     			                    .append(" GROUP BY P.ID, C.NUMERO ")
     			                      .append(" HAVING MAX(L.STATUS_ID) NOT IN ( ").append(joiner.toString()).append(" ) ");
		
		broker.setSQL(sql.toString());
		return broker.getCollectionBean(Pedido.class, "id", "conta.numero");
	}
	
	public List<Pedido> getAllByAreaAndStatus(Area area, Status status) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidodao.findallbystatusandarea", area.getId(), status.getId());
		return broker.getCollectionBean(Pedido.class, "id", "observacao", "conta.numero", "horarioSolicitacao");
	}

	@Override
	public Pedido insert(Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		model.setId(broker.getSequenceNextValue("dbo.pedidos"));

		broker.setPropertySQL("pedidodao.insert", model.getConta().getId(), model.getObservacao());

		broker.execute();

		for (PedidoSubItem pedidoSubItem : model.getSubItens()) {

			pedidoSubItem.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens"));

			broker.setPropertySQL("pedidosubitemdao.insertbypedido", model.getId(), pedidoSubItem.getQuantidade(), pedidoSubItem.getSubItem().getId(), pedidoSubItem.getSubItem().getId(), pedidoSubItem.getKit() != null ? pedidoSubItem.getKit().getId() : null);

			broker.execute();

		}

		broker.endTransaction();

		return model;

	}

	@Override
	public Pedido update(final Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		broker.setPropertySQL("pedidodao.updateobs", model.getObservacao(), model.getId());

		broker.execute();

		broker.setPropertySQL("pedidodao.alterarresponsavel", model.getUsuario().getId(), model.getId());

		broker.execute();

		PedidoSubItemDAO pedidoSubItemDAO = new PedidoSubItemDAO();

		for (PedidoSubItem item : model.getSubItens()) {

			item.setStatus(new Status(Constantes.StatusPedido.PENDENTE_ENTREGA));

			if (TSUtil.isEmpty(item.getId())) {

				item.setPedido(model);

				item = pedidoSubItemDAO.insert(item, broker);

			} else {

				item = pedidoSubItemDAO.update(item, broker);

			}

		}

		broker.endTransaction();

		return model;

	}

	public void aprovar(final Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.aprovar", model.getId());

		broker.execute();

	}

	public void cancelar(Pedido model, Usuario usuario) throws TSApplicationException {

		if (model.getSubItens() == null || model.getSubItens().size() == 0)
			model.setSubItens(new PedidoSubItemDAO().getAll(model));

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.beginTransaction();

		Log log;
		for (PedidoSubItem pedidoSubItem : model.getSubItens()) {
			log = new Log();
			log.setPedidoSubItem(pedidoSubItem);
			log.setStatus(new Status(Constantes.StatusPedido.CANCELADO));
			log.setUsuario(usuario);

			new LogDAO().insert(log, broker);
		}

		broker.endTransaction();
	}

	public void aprovar(Pedido model, Usuario usuario) throws TSApplicationException {

		if (model.getSubItens() == null || model.getSubItens().size() == 0)
			model.setSubItens(new PedidoSubItemDAO().getAll(model));

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.beginTransaction();

		Log log;
		for (PedidoSubItem pedidoSubItem : model.getSubItens()) {
			log = new Log();
			log.setPedidoSubItem(pedidoSubItem);
			log.setStatus(new Status(Constantes.StatusPedido.PENDENTE_ENTREGA));
			log.setUsuario(usuario);

			new LogDAO().insert(log, broker);
		}

		broker.endTransaction();
	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.delete", id);

		broker.execute();

	}

}
