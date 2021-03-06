package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class ContaDAO  implements RestDAO<Conta> {

	@Override
	public Conta get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.get", id);

		return (Conta) broker.getObjectBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");

	}

	@Override
	public List<Conta> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.findall");

		return broker.getCollectionBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");

	}

	@Override
	public Conta insert(Conta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.contas "));

		broker.setPropertySQL("contadao.insert",model.getCliente().getId(), model.getDataAbertura(), model.getDataFechamento(), model.getNumero(), model.getQtdPessoa(), model.getTipoConta());

		broker.execute();

		return model;

	}

	@Override
	public Conta update(final Conta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.update", model.getCliente().getId(), model.getDataAbertura(), model.getDataFechamento(), model.getNumero(), model.getQtdPessoa(), model.getTipoConta(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("contadao.delete", id);

		broker.execute();

	}

}
