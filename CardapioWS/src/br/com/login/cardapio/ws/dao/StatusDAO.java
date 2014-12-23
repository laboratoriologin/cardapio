package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Status;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class StatusDAO  implements RestDAO<Status> {

	@Override
	public Status get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("statusdao.get", id);

		return (Status) broker.getObjectBean(Status.class, "descricao", "id");

	}

	@Override
	public List<Status> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("statusdao.findall");

		return broker.getCollectionBean(Status.class, "descricao", "id");

	}

	@Override
	public Status insert(Status model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("status_id_seq"));

		broker.setPropertySQL("statusdao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public Status update(final Status model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("statusdao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("statusdao.delete", id);

		broker.execute();

	}

}