package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.CategoriaCardapio;
import br.com.login.cardapio.ws.model.Empresa;
import br.com.login.cardapio.ws.model.Item;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class ItemDAO implements RestDAO<Item> {

	@Override
	public Item get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.get", id);

		return (Item) broker.getObjectBean(Item.class, "descricao", "guarnicoes", "id", "empresaCategoriaCardapio.id", "imagem", "ingredientes", "nome", "tempoMedioPreparo");

	}

	@Override
	public List<Item> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.findall");

		return broker.getCollectionBean(Item.class, "descricao", "guarnicoes", "id", "empresaCategoriaCardapio.id", "imagem", "ingredientes", "nome", "tempoMedioPreparo");

	}

	@SuppressWarnings("unchecked")
	public List<Item> getAll(Empresa argEmpresa, CategoriaCardapio categoriaCardapio) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.findallbyempresacategoria", argEmpresa.getKeyMobile(), categoriaCardapio.getId());

		List<Item> listaItem = broker.getCollectionBean(Item.class, "id", "nome", "descricao", "guarnicoes", "ingredientes", "imagem", "empresaCategoriaCardapio.id", "empresaCategoriaCardapio.categoriaCardapio.id", "tempoMedioPreparo");
		SubItemDAO subItemDAO = new SubItemDAO();

		for (Item item : listaItem) {
			item.setSubItens(subItemDAO.getAll(item));
		}

		return listaItem;
	}

	@Override
	public Item insert(Item model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("itens_id_seq"));

		broker.setPropertySQL("itemdao.insert", model.getDescricao(), model.getGuarnicoes(), model.getEmpresaCategoriaCardapio().getId(), model.getImagem(), model.getIngredientes(), model.getNome(), model.getTempoMedioPreparo());

		broker.execute();

		return model;

	}

	@Override
	public Item update(final Item model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("itemdao.update", model.getDescricao(), model.getGuarnicoes(), model.getEmpresaCategoriaCardapio().getId(), model.getImagem(), model.getIngredientes(), model.getNome(), model.getTempoMedioPreparo(), model.getId());

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
