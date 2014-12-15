package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
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
		
		broker.beginTransaction();

		model.setId(broker.getSequenceNextValue("dbo.pedidos"));

		broker.setPropertySQL("pedidodao.insert", model.getConta().getId(), model.getObservacao());

		broker.execute();
		
		for (PedidoSubItem pedidoSubItem : model.getSubItens()) {
			
			pedidoSubItem.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens"));
			
			broker.setPropertySQL("pedidosubitemdao.insertbypedido",model.getId(), pedidoSubItem.getQuantidade(), pedidoSubItem.getSubItem().getId(), pedidoSubItem.getSubItem().getId());
			
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

			item.setStatus(new Status(Constantes.PEDIDO_PENDENTE_ENTREGA));

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

	public void cancelar(Pedido model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.cancelar", model.getId());

		broker.execute();

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidodao.delete", id);

		broker.execute();

	}

}
