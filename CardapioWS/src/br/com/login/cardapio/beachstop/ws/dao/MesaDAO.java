package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class MesaDAO  implements RestDAO<Mesa> {

	@Override
	public Mesa get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("mesadao.get", id);

		return (Mesa) broker.getObjectBean(Mesa.class, "id", "numero", "setor.id");

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

		broker.setPropertySQL("mesadao.insert",model.getNumero(), model.getSetor().getId());

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
