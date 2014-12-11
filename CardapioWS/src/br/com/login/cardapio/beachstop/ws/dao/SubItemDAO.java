package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Item;
import br.com.login.cardapio.beachstop.ws.model.SubItem;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class SubItemDAO implements RestDAO<SubItem> {

	@Override
	public SubItem get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.get", id);

		return (SubItem) broker.getObjectBean(SubItem.class, "codigo", "descricao", "flagAtivo", "id", "item.id", "nome", "ordem", "valor");

	}

	@Override
	public List<SubItem> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.findall");

		return broker.getCollectionBean(SubItem.class, "codigo", "descricao", "flagAtivo", "id", "item.id", "nome", "ordem", "valor");

	}

	public List<SubItem> getAll(Item item) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.findallbyitem", item.getId());

		return broker.getCollectionBean(SubItem.class, "codigo", "descricao", "flagAtivo", "id", "item.id", "nome", "ordem", "valor");

	}

	@Override
	public SubItem insert(SubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.sub_itens "));

		broker.setPropertySQL("subitemdao.insert", model.getCodigo(), model.getDescricao(), model.getFlagAtivo(), model.getItem().getId(), model.getNome(), model.getOrdem(), model.getValor());

		broker.execute();

		return model;

	}

	@Override
	public SubItem update(final SubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.update", model.getCodigo(), model.getDescricao(), model.getFlagAtivo(), model.getItem().getId(), model.getNome(), model.getOrdem(), model.getValor(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.delete", id);

		broker.execute();

	}

}
