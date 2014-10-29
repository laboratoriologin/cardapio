package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Categoria;
import br.com.login.cardapio.beachstop.ws.model.Item;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class ItemDAO implements RestDAO<Item> {

	@Override
	public Item get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.get", id);

		return (Item) broker.getObjectBean(Item.class, "categoria.id", "descricao", "flagAtivo", "id", "image", "ingrediente", "nome", "ordem", "tempoPreparo");

	}

	@Override
	public List<Item> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.findall");

		return broker.getCollectionBean(Item.class, "categoria.id", "descricao", "flagAtivo", "id", "image", "ingrediente", "nome", "ordem", "tempoPreparo");

	}

	@SuppressWarnings("unchecked")
	public List<Item> getAll(Categoria categoria) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.findallbycategoria", categoria.getId());

		List<Item> listaItem = broker.getCollectionBean(Item.class, "categoria.id", "descricao", "flagAtivo", "id", "image", "ingrediente", "nome", "ordem", "tempoPreparo");
		SubItemDAO subItemDAO = new SubItemDAO();

		for (Item item : listaItem) {
			item.setSubItens(subItemDAO.getAll(item));
		}

		return listaItem;
	}

	@Override
	public Item insert(Item model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.itens "));

		broker.setPropertySQL("itemdao.insert", model.getCategoria().getId(), model.getDescricao(), model.getFlagAtivo(), model.getImage(), model.getIngrediente(), model.getNome(), model.getOrdem(), model.getTempoPreparo());

		broker.execute();

		return model;

	}

	@Override
	public Item update(final Item model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.update", model.getCategoria().getId(), model.getDescricao(), model.getFlagAtivo(), model.getImage(), model.getIngrediente(), model.getNome(), model.getOrdem(), model.getTempoPreparo(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.delete", id);

		broker.execute();

	}

}
