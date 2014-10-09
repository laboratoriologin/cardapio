package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.TipoAgenda;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoAgendaDAO  implements RestDAO<TipoAgenda> {

	@Override
	public TipoAgenda get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoagendadao.get", id);

		return (TipoAgenda) broker.getObjectBean(TipoAgenda.class, "descricao", "id");

	}

	@Override
	public List<TipoAgenda> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoagendadao.findall");

		return broker.getCollectionBean(TipoAgenda.class, "descricao", "id");

	}

	@Override
	public TipoAgenda insert(TipoAgenda model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tipos_agenda_id_seq"));

		broker.setPropertySQL("tipoagendadao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public TipoAgenda update(final TipoAgenda model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoagendadao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoagendadao.delete", id);

		broker.execute();

	}

}
