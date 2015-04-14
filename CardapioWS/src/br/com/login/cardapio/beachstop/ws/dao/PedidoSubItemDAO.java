package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class PedidoSubItemDAO implements RestDAO<PedidoSubItem> {

	@Override
	public PedidoSubItem get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.get", id);

		return (PedidoSubItem) broker.getObjectBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario");

	}

	public List<PedidoSubItem> getAllOuterStatus(Pedido pedido, Status status) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.getbypedidostatus", pedido.getId(), status.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "quantidade", "subItem.item.nome", "subItem.nome", "status.id");
	}
	
	public List<PedidoSubItem> getAllByPedidoHasStatus(Pedido pedido) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.getallbypedidohasstatus", pedido.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "id", "quantidade", "subItem.item.nome", "subItem.nome", "status.id", "status.descricao");
	}

	public List<PedidoSubItem> getAllPedidoStatusArea(Pedido pedido, Status status, Area area) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.getallbystatuspedidoarea", pedido.getId(), area.getId(), status.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "id", "quantidade", "subItem.item.nome", "subItem.nome", "status.id");
	}

	@Override
	public List<PedidoSubItem> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.findall");

		return broker.getCollectionBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario");

	}

	public List<PedidoSubItem> getAll(Conta conta) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.findallbyconta", conta.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario", "valorCalculado");
	}

	public List<PedidoSubItem> getAllGroupQtd(Conta conta) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.getallbycontagroupqtd", conta.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "quantidade", "subItem.nome", "subItem.codigo", "subItem.item.nome");
	}

	public List<PedidoSubItem> getAll(Pedido pedido) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.findallbypedido", pedido.getId());

		return broker.getCollectionBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario");

	}

	public List<PedidoSubItem> getAll(Pedido pedido, Status status) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.findallbypedidostatus", pedido.getId(), pedido.getId(), status.getId());

		return broker.getCollectionBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario", "kit.id", "kit.descricao");

	}

	@Override
	public PedidoSubItem insert(PedidoSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens "));

		broker.setPropertySQL("pedidosubitemdao.insert", model.getPedido().getId(), model.getQuantidade(), model.getSubItem().getId(), model.getValorUnitario(), model.getKit() != null ? model.getKit().getId() : null);

		broker.execute();

		return model;

	}

	public PedidoSubItem insert(PedidoSubItem model, TSDataBaseBrokerIf broker) throws TSApplicationException {

		model.setId(broker.getSequenceNextValue("dbo.pedidos_sub_itens"));

		broker.setPropertySQL("itempedidodao.insert", model.getPedido(), model.getSubItem().getId(), model.getQuantidade(), model.getStatus().getId());

		broker.execute();

		return model;

	}

	@Override
	public PedidoSubItem update(final PedidoSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pedidosubitemdao.updatequantidade", model.getQuantidade(), model.getId());

		broker.execute();

		return model;

	}

	public PedidoSubItem update(final PedidoSubItem model, TSDataBaseBrokerIf broker) throws TSApplicationException {

		broker.setPropertySQL("pedidosubitemdao.updatequantidade", model.getQuantidade(), model.getId());

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
