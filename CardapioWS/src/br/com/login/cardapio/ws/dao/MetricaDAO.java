package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Alerta;
import br.com.login.cardapio.ws.model.Empresa;
import br.com.login.cardapio.ws.model.Metrica;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

public class MetricaDAO {

	public List<Metrica> getAll(Empresa empresa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("metricadao.findallbyempresa", empresa.getKeyMobile());

		return broker.getCollectionBean(Metrica.class, "id", "empresa.id", "tipoAlerta.id", "valor");

	}

}
