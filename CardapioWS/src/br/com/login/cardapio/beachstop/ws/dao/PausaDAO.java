package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Pausa;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PausaDAO  implements RestDAO<Pausa> {

	@Override
	public Pausa get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.get", id);

		return (Pausa) broker.getObjectBean(Pausa.class, "horarioFinal", "horarioInicial", "id", "usuario.id");

	}

	@Override
	public List<Pausa> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.findall");

		return broker.getCollectionBean(Pausa.class, "horarioFinal", "horarioInicial", "id", "usuario.id");

	}

	@Override
	public Pausa insert(Pausa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pausas "));

		broker.setPropertySQL("pausadao.insert",model.getHorarioFinal(), model.getHorarioInicial(), model.getUsuario().getId());

		broker.execute();

		return model;

	}

	@Override
	public Pausa update(final Pausa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.update", model.getHorarioFinal(), model.getHorarioInicial(), model.getUsuario().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.delete", id);

		broker.execute();

	}

}
