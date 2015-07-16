package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.login.cardapio.beachstop.ws.model.Setor;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class MesaDAO implements RestDAO<Mesa> {

	@Override
	public Mesa get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("mesadao.get", id);

		return (Mesa) broker.getObjectBean(Mesa.class, "id", "numero", "setor.id");

	}

	public List<Mesa> getAll(Setor setor) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		String sql = "SELECT M.ID, M.NUMERO, C.ID, C.QTD_PESSOA, SUM( (PSI.QUANTIDADE * PSI.VALOR_UNITARIO) ) AS VALOR_TOTAL, (SELECT AC.ID FROM ACOES_CONTAS AS AC WHERE C.ID = AC.CONTA_ID AND ACAO_ID = 4) AS ACAO_CONTA_ID "
				+ "     FROM MESAS AS M "
				+ "LEFT JOIN CONTAS AS C ON M.NUMERO = C.NUMERO AND C.DATA_FECHAMENTO IS NULL "
				+ "LEFT JOIN PEDIDOS AS P ON P.CONTA_ID = C.ID "
				+ "LEFT JOIN PEDIDOS_SUB_ITENS AS PSI ON PSI.PEDIDO_ID = P.ID "
				+ "    WHERE NOT EXISTS (SELECT 1 FROM LOGS AS L WHERE PSI.ID = L.PEDIDO_SUB_ITEM_ID AND L.STATUS_ID = 4) ";
		
		if(setor != null){
			sql += "AND SETOR_ID = " + setor.getId(); 
		}
				
		sql += "GROUP BY M.ID, M.NUMERO, C.ID, C.QTD_PESSOA";
		
		broker.setSQL(sql);
		
		return broker.getCollectionBean(Mesa.class, "id", "numero", "conta.id", "conta.qtdPessoa", "conta.valor", "conta.acaoConta.id");
	}

	@Override
	public List<Mesa> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("mesadao.findall");

		return broker.getCollectionBean(Mesa.class, "id", "numero", "setor.id");

	}

	@Override
	public Mesa insert(Mesa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.mesas "));

		broker.setPropertySQL("mesadao.insert", model.getNumero(), model.getSetor().getId());

		broker.execute();

		return model;

	}

	@Override
	public Mesa update(final Mesa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("mesadao.update", model.getNumero(), model.getSetor().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("mesadao.delete", id);

		broker.execute();

	}

}
