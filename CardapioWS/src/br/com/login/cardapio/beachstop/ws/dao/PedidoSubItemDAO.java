package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.model.SubItem;
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
	
	public List<PedidoSubItem> getByContaSubItem(Conta conta, SubItem subItem) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder(" SELECT PSI.ID, PSI.QUANTIDADE, CONVERT(VARCHAR(8), L.HORARIO, 108) AS HORARIO, U.NOME, (SELECT DESCRICAO FROM STATUS WHERE ID = (SELECT MAX(STATUS_ID) FROM LOGS AS L1 WHERE L1.PEDIDO_SUB_ITEM_ID = PSI.ID)) AS STATUS  ");
         							 sql.append(" FROM CONTAS AS C  ")
     			                  .append(" INNER JOIN PEDIDOS AS P ON C.ID = P.CONTA_ID ")
     			                  .append(" INNER JOIN PEDIDOS_SUB_ITENS AS PSI ON P.ID = PSI.PEDIDO_ID ")
     			                  .append(" INNER JOIN SUB_ITENS AS SI ON PSI.SUB_ITEM_ID = SI.ID ")
     			                  .append(" INNER JOIN LOGS AS L ON PSI.ID = L.PEDIDO_SUB_ITEM_ID ")
     			                  .append(" INNER JOIN USUARIOS AS U ON L.USUARIO_ID = U.ID ")
     			                    .append("    WHERE C.ID = ").append(conta.getId()).append(" AND SI.ID = ").append(subItem.getId()).append("AND L.STATUS_ID = ").append(Constantes.StatusPedido.ENTREGUE)
     			                  .append(" UNION ")  
     			                      .append(" SELECT PSI.ID, PSI.QUANTIDADE, CONVERT(VARCHAR(8), L.HORARIO, 108) AS HORARIO, U.NOME, (SELECT DESCRICAO FROM STATUS WHERE ID = (SELECT MAX(STATUS_ID) FROM LOGS AS L1 WHERE L1.PEDIDO_SUB_ITEM_ID = PSI.ID)) AS STATUS  ")
      							        .append(" FROM CONTAS AS C  ")
    			                  .append(" INNER JOIN PEDIDOS AS P ON C.ID = P.CONTA_ID ")
    			                  .append(" INNER JOIN PEDIDOS_SUB_ITENS AS PSI ON P.ID = PSI.PEDIDO_ID ")
    			                  .append(" INNER JOIN SUB_ITENS AS SI ON PSI.SUB_ITEM_ID = SI.ID ")
    			                  .append(" INNER JOIN LOGS AS L ON PSI.ID = L.PEDIDO_SUB_ITEM_ID ")
    			                  .append(" INNER JOIN USUARIOS AS U ON L.USUARIO_ID = U.ID ")
    			                    .append("    WHERE C.ID = ").append(conta.getId()).append(" AND SI.ID = ").append(subItem.getId()).append("AND L.STATUS_ID = ").append(Constantes.StatusPedido.PENDENTE_ENTREGA)
		                    	  .append(" UNION ")  
     			                      .append(" SELECT PSI.ID, PSI.QUANTIDADE, CONVERT(VARCHAR(8), L.HORARIO, 108) AS HORARIO, U.NOME, (SELECT DESCRICAO FROM STATUS WHERE ID = (SELECT MAX(STATUS_ID) FROM LOGS AS L1 WHERE L1.PEDIDO_SUB_ITEM_ID = PSI.ID)) AS STATUS  ")
      							        .append(" FROM CONTAS AS C  ")
    			                  .append(" INNER JOIN PEDIDOS AS P ON C.ID = P.CONTA_ID ")
    			                  .append(" INNER JOIN PEDIDOS_SUB_ITENS AS PSI ON P.ID = PSI.PEDIDO_ID ")
    			                  .append(" INNER JOIN SUB_ITENS AS SI ON PSI.SUB_ITEM_ID = SI.ID ")
    			                  .append(" INNER JOIN LOGS AS L ON PSI.ID = L.PEDIDO_SUB_ITEM_ID ")
    			                  .append(" INNER JOIN USUARIOS AS U ON L.USUARIO_ID = U.ID ")
    			                    .append("    WHERE C.ID = ").append(conta.getId()).append(" AND SI.ID = ").append(subItem.getId()).append("AND L.STATUS_ID = ").append(Constantes.StatusPedido.PENDENTE_APROVACAO);
		
        broker.setSQL(sql.toString());
		return broker.getCollectionBean(PedidoSubItem.class, "id", "quantidade", "log.strHorario", "log.usuario.nome", "status.descricao");
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
	
	public List<PedidoSubItem> getAllGroupQtdCelular(Conta conta) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.findallbycontacelular", conta.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "quantidade", "subItem.id", "valorUnitario", "valorCalculado");
	}

	public List<PedidoSubItem> getAllGroupQtd(Conta conta) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.getallbycontagroupqtd", conta.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "quantidade", "subItem.id", "subItem.nome", "subItem.codigo", "subItem.item.nome");
	}

	public List<PedidoSubItem> getAll(Pedido pedido) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pedidosubitemdao.findallbypedido", pedido.getId());
		return broker.getCollectionBean(PedidoSubItem.class, "id", "pedido.id", "quantidade", "subItem.id", "valorUnitario",  "valorCalculado");
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
