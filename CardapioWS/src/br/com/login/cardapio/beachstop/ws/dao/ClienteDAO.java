package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Cliente;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class ClienteDAO  implements RestDAO<Cliente> {

	@Override
	public Cliente get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("clientedao.get", id);

		return (Cliente) broker.getObjectBean(Cliente.class, "celular", "dataNascimento", "email", "id", "nome", "tokenAndroid", "tokenIos");

	}

	@Override
	public List<Cliente> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("clientedao.findall");

		return broker.getCollectionBean(Cliente.class, "celular", "dataNascimento", "email", "id", "nome", "tokenAndroid", "tokenIos");

	}

	@Override
	public Cliente insert(Cliente model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.clientes "));

		broker.setPropertySQL("clientedao.insert",model.getCelular(), model.getDataNascimento(), model.getEmail(), model.getNome(), model.getTokenAndroid(), model.getTokenIos());

		broker.execute();

		return model;

	}

	@Override
	public Cliente update(final Cliente model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("clientedao.update", model.getCelular(), model.getDataNascimento(), model.getEmail(), model.getNome(), model.getTokenAndroid(), model.getTokenIos(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("clientedao.delete", id);

		broker.execute();

	}

}
