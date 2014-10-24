package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.KitSubItem;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class KitSubItemDAO  implements RestDAO<KitSubItem> {

	@Override
	public KitSubItem get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitsubitemdao.get", id);

		return (KitSubItem) broker.getObjectBean(KitSubItem.class, "id", "kit.id", "subItem.id");

	}

	@Override
	public List<KitSubItem> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitsubitemdao.findall");

		return broker.getCollectionBean(KitSubItem.class, "id", "kit.id", "subItem.id");

	}

	@Override
	public KitSubItem insert(KitSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.kits_sub_itens "));

		broker.setPropertySQL("kitsubitemdao.insert",model.getKit().getId(), model.getSubItem().getId());

		broker.execute();

		return model;

	}

	@Override
	public KitSubItem update(final KitSubItem model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitsubitemdao.update", model.getKit().getId(), model.getSubItem().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitsubitemdao.delete", id);

		broker.execute();

	}

}
