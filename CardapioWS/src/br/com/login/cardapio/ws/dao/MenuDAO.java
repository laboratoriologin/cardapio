package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Menu;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class MenuDAO  implements RestDAO<Menu> {

	@Override
	public Menu get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("menudao.get", id);

		return (Menu) broker.getObjectBean(Menu.class, "descricao", "flagAtivo", "id", "managedBeanReset", "menu.id", "ordem", "url");

	}

	@Override
	public List<Menu> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("menudao.findall");

		return broker.getCollectionBean(Menu.class, "descricao", "flagAtivo", "id", "managedBeanReset", "menu.id", "ordem", "url");

	}

	@Override
	public Menu insert(Menu model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("menus_id_seq"));

		broker.setPropertySQL("menudao.insert",model.getDescricao(), model.getFlagAtivo(), model.getManagedBeanReset(), model.getMenu().getId(), model.getOrdem(), model.getUrl());

		broker.execute();

		return model;

	}

	@Override
	public Menu update(final Menu model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("menudao.update", model.getDescricao(), model.getFlagAtivo(), model.getManagedBeanReset(), model.getMenu().getId(), model.getOrdem(), model.getUrl(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("menudao.delete", id);

		broker.execute();

	}

}
