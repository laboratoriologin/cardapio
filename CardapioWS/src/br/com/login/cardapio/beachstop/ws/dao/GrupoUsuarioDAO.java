package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.GrupoUsuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class GrupoUsuarioDAO  implements RestDAO<GrupoUsuario> {

	@Override
	public GrupoUsuario get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupousuariodao.get", id);

		return (GrupoUsuario) broker.getObjectBean(GrupoUsuario.class, "descricao", "id");

	}

	@Override
	public List<GrupoUsuario> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupousuariodao.findall");

		return broker.getCollectionBean(GrupoUsuario.class, "descricao", "id");

	}

	@Override
	public GrupoUsuario insert(GrupoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.grupos_usuarios "));

		broker.setPropertySQL("grupousuariodao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public GrupoUsuario update(final GrupoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupousuariodao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupousuariodao.delete", id);

		broker.execute();

	}

}
