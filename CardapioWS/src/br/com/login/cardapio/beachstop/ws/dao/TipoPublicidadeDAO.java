package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.TipoPublicidade;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoPublicidadeDAO  implements RestDAO<TipoPublicidade> {

	@Override
	public TipoPublicidade get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopublicidadedao.get", id);

		return (TipoPublicidade) broker.getObjectBean(TipoPublicidade.class, "descricao", "id");

	}

	@Override
	public List<TipoPublicidade> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopublicidadedao.findall");

		return broker.getCollectionBean(TipoPublicidade.class, "descricao", "id");

	}

	@Override
	public TipoPublicidade insert(TipoPublicidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.tipos_publicidades "));

		broker.setPropertySQL("tipopublicidadedao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public TipoPublicidade update(final TipoPublicidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopublicidadedao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopublicidadedao.delete", id);

		broker.execute();

	}

}
