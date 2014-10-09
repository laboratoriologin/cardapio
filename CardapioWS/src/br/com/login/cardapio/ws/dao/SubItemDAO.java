package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Item;
import br.com.login.cardapio.ws.model.SubItem;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class SubItemDAO implements RestDAO<SubItem> {

	@Override
	public SubItem get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.get", id);

		return (SubItem) broker.getObjectBean(SubItem.class, "descricao", "id", "item.id", "tipoQuantidade.id", "quantidade", "valor", "codSubItem");

	}

	@Override
	public List<SubItem> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.findall");

		return broker.getCollectionBean(SubItem.class, "descricao", "id", "item.id", "tipoQuantidade.id", "quantidade", "valor", "codSubItem");

	}

	@SuppressWarnings("unchecked")
	public List<SubItem> getAll(Item item) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.findallbyitemid", item.getId());

		return broker.getCollectionBean(SubItem.class, "id", "descricao", "valor", "item.id", "tipoQuantidade.id", "tipoQuantidade.descricao", "quantidade", "codSubItem");
	}

	@Override
	public SubItem insert(SubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("sub_itens_id_seq"));

		broker.setPropertySQL("subitemdao.insert", model.getDescricao(), model.getItem().getId(), model.getTipoQuantidade().getId(), model.getQuantidade(), model.getValor(), model.getCodSubItem());

		broker.execute();

		return model;

	}

	@Override
	public SubItem update(final SubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("subitemdao.update", model.getDescricao(), model.getItem().getId(), model.getTipoQuantidade().getId(), model.getQuantidade(), model.getValor(), model.getId(), model.getCodSubItem());

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
