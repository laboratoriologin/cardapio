package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Conta;
import br.com.login.cardapio.ws.model.PedidoSubItem;
import br.com.login.cardapio.ws.model.Status;
import br.com.login.cardapio.ws.model.SubItem;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class ContaDAO implements RestDAO<Conta> {

	@Override
	public Conta get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.get", id);

		Conta conta = (Conta) broker.getObjectBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

		if (conta != null) {

			conta.setPedidoSubItem(new PedidoSubItemDAO().getAll(conta));

		}

		return conta;

	}

	public List<Conta> getPedidosNaoEntregueCancelado(String keyMobile, String nMesa, Boolean booLog) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder("SELECT C.FLAG_ABERTO, C.HORARIO_CHEGADA, C.HORARIO_SAIDA, C.ID, C.ID_EMPRESA, C.MESA, C.VALOR, C.VALOR_PAGO FROM CONTAS AS C, EMPRESAS AS E WHERE C.ID_EMPRESA = E.ID AND C.MESA IN (");

		sql.append(nMesa).append(") AND E.KEY_MOBILE = ? AND C.FLAG_ABERTO = 1 AND EXISTS (SELECT 1 FROM PEDIDOS_SUB_ITENS AS PSI INNER JOIN PEDIDOS AS P ON P.ID =  PSI.ID_PEDIDO WHERE P.ID_CONTA = C.ID AND PSI.ID_STATUS NOT IN (3,4) )");

		broker.setSQL(sql.toString(), keyMobile);

		List<Conta> listConta = broker.getCollectionBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

		if (listConta != null) {

			for (Conta conta : listConta) {

				conta.setPedido(new PedidoDAO().getAllNaoEntregueCancelado(conta, booLog));

			}

		}

		return listConta;
	}

	public List<Conta> getNovosPedidosByEmpresaMesa(String keyMobile, String nMesa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder("SELECT C.FLAG_ABERTO, C.HORARIO_CHEGADA, C.HORARIO_SAIDA, C.ID, C.ID_EMPRESA, C.MESA, C.VALOR, C.VALOR_PAGO FROM CONTAS AS C, EMPRESAS AS E WHERE C.ID_EMPRESA = E.ID AND C.MESA IN (");

		sql.append(nMesa).append(") AND E.KEY_MOBILE = ? AND C.FLAG_ABERTO = 1 AND EXISTS (SELECT 1 FROM PEDIDOS_SUB_ITENS AS PSI INNER JOIN PEDIDOS AS P ON P.ID =  PSI.ID_PEDIDO WHERE P.ID_CONTA = C.ID AND PSI.ID_STATUS = 1 )");

		broker.setSQL(sql.toString(), keyMobile);

		List<Conta> listConta = broker.getCollectionBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

		if (listConta != null) {

			for (Conta conta : listConta) {

				conta.setPedido(new PedidoDAO().getAllPendente(conta));

			}

		}

		return listConta;
	}

	public Conta getByMesaEmpresa(Long mesa, String keyMobile) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.getbymesakeyempresa", mesa, keyMobile);

		Conta conta = (Conta) broker.getObjectBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

		if (conta != null) {
			conta.setPedidoSubItem(new PedidoSubItemDAO().getAll(conta));
		}

		return conta;

	}

	public Conta getByMesaEmpresa(Long mesa, Long idEmpresa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.getbymesaempresa", mesa, idEmpresa);

		Conta conta = (Conta) broker.getObjectBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

		if (conta != null) {
			conta.setPedidoSubItem(new PedidoSubItemDAO().getAll(conta));
		}

		return conta;

	}

	public List<Conta> getOcupadas(Long idEmpresa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.getocupadas", idEmpresa);

		List<Conta> listConta = broker.getCollectionBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

		return listConta;

	}

	public Boolean isBusyByTable(Conta model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.isbusybytable", model.getMesa(), model.getEmpresa().getId());

		@SuppressWarnings("unchecked")
		Conta conta = (Conta) broker.getObjectBean(Conta.class, "id");

		if (conta == null || conta.getId() == null)
			return false;
		else
			return true;

	}

	public Boolean isBusyById(Conta model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.isbusybyid", model.getId(), model.getEmpresa().getId());

		@SuppressWarnings("unchecked")
		Conta conta = (Conta) broker.getObjectBean(Conta.class, "id");

		if (conta == null || conta.getId() == null)
			return false;
		else
			return true;

	}

	@Override
	public List<Conta> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.findall");

		return broker.getCollectionBean(Conta.class, "flagAberto", "horarioChegada", "horarioSaida", "id", "empresa.id", "mesa", "valor", "valorPago");

	}

	@Override
	public Conta insert(Conta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.contas"));

		broker.setPropertySQL("contadao.insert", model.getFlagAberto(), model.getHorarioChegada(), model.getHorarioSaida(), model.getEmpresa().getId(), model.getMesa(), model.getValor(), model.getValorPago());

		broker.execute();

		return model;

	}

	@Override
	public Conta update(final Conta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.update", model.getMesa(), model.getId());

		broker.execute();

		return model;

	}

	public Conta updateFecharConta(final Conta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.updateFecharConta", model.getFlagAberto(), model.getId());

		broker.execute();

		return model;

	}

	public Conta updateValorTotal(Conta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		Conta conta = new ContaDAO().get(model.getId());

		SubItem subItem;
		SubItemDAO subItemDAO = new SubItemDAO();

		conta.setValor(0d);

		for (PedidoSubItem item : conta.getPedidoSubItem()) {

			if (!item.getStatus().getId().equals(Status.CANCELADO)) {

				subItem = subItemDAO.get(item.getSubitem().getId());

				conta.setValor(conta.getValor() + (item.getQuantidade() * subItem.getValor()));

			}

		}

		broker.setPropertySQL("contadao.updatevalortotal", conta.getValor(), conta.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.delete", id);

		broker.execute();

	}
}
