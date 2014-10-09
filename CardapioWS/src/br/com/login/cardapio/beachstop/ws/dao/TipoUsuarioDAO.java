package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.TipoUsuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoUsuarioDAO  implements RestDAO<TipoUsuario> {

	@Override
	public TipoUsuario get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.get", id);

		return (TipoUsuario) broker.getObjectBean(TipoUsuario.class, "descricao", "id");

	}

	@Override
	public List<TipoUsuario> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.findall");

		return broker.getCollectionBean(TipoUsuario.class, "descricao", "id");

	}

	@Override
	public TipoUsuario insert(TipoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.tipos_usuarios "));

		broker.setPropertySQL("tipousuariodao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public TipoUsuario update(final TipoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.delete", id);

		broker.execute();

	}

}
