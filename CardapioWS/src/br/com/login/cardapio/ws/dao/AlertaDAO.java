package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Alerta;
import br.com.login.cardapio.ws.model.Empresa;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class AlertaDAO implements RestDAO<Alerta> {

	@Override
	public Alerta get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("alertadao.get", id);

		return (Alerta) broker.getObjectBean(Alerta.class, "id", "conta.id", "flagResolvido", "metrica.id");

	}

	@Override
	public List<Alerta> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("alertadao.findall");

		return broker.getCollectionBean(Alerta.class, "id", "conta.id", "flagResolvido", "metrica.id");

	}

	public List<Alerta> getAllOpen(Empresa empresa, String mMesa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder("SELECT A.ID, TA.ID, A.HORARIO, A.ID_CONTA, C.MESA, M.ID, M.ID_EMPRESA, M.VALOR, TA.ID, TA. DESCRICAO FROM ALERTAS A, CONTAS C,METRICAS M, TIPOS_ALERTAS TA, EMPRESAS E WHERE A.ID_CONTA = C.ID AND C.ID_EMPRESA = E.ID AND A.ID_METRICA = M.ID AND M.ID_TIPO_ALERTA = TA.ID AND A.FLAG_RESOLVIDO = 0 AND E.KEY_MOBILE = ? AND C.MESA IN (");
		sql.append(mMesa).append(")");

		broker.setSQL(sql.toString(), empresa.getKeyMobile());

		return broker.getCollectionBean(Alerta.class, "id", "tipoAlerta.id" ,"horario", "conta.id", "conta.mesa", "metrica.id", "metrica.empresa.id", "metrica.valor", "metrica.tipoAlerta.id", "metrica.tipoAlerta.descricao");
	}

	@Override
	public Alerta insert(Alerta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.alertas"));

		// O id da conta repete nos parametros pois precisa buscar a metrica a
		// partir do tipo alerta e a empresa. A empresa é buscada pelo numero da
		// conta
		broker.setPropertySQL("alertadao.insert", model.getTipoAlerta().getId(), model.getConta().getId(), model.getFlagResolvido(), model.getConta().getId());

		broker.execute();

		return model;

	}

	@Override
	public Alerta update(final Alerta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("alertadao.update", model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("Alertadao.delete", id);

		broker.execute();

	}

}
