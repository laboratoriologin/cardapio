package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.AgendaPublicidade;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
//teste switch branch
public class AgendaPublicidadeDAO  implements RestDAO<AgendaPublicidade> {

	@Override
	public AgendaPublicidade get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("agendapublicidadedao.get", id);

		return (AgendaPublicidade) broker.getObjectBean(AgendaPublicidade.class, "id", "publicidade.id", "tipoAgenda.id", "valor");

	}

	@Override
	public List<AgendaPublicidade> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("agendapublicidadedao.findall");

		return broker.getCollectionBean(AgendaPublicidade.class, "id", "publicidade.id", "tipoAgenda.id", "valor");

	}

	@Override
	public AgendaPublicidade insert(AgendaPublicidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("agendas_publicidade_id_seq"));

		broker.setPropertySQL("agendapublicidadedao.insert",model.getId(), model.getPublicidade().getId(), model.getTipoAgenda().getId(), model.getValor());

		broker.execute();

		return model;

	}

	@Override
	public AgendaPublicidade update(final AgendaPublicidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("agendapublicidadedao.update", model.getId(), model.getPublicidade().getId(), model.getTipoAgenda().getId(), model.getValor(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("agendapublicidadedao.delete", id);

		broker.execute();

	}

}
