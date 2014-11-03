package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.CategoriaCardapio;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class CategoriaCardapioDAO implements RestDAO<CategoriaCardapio> {

	@Override
	public CategoriaCardapio get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriacardapiodao.get", id);

		return (CategoriaCardapio) broker.getObjectBean(CategoriaCardapio.class, "descricao", "id", "imagem");

	}

	@Override
	public List<CategoriaCardapio> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriacardapiodao.findall");

		return broker.getCollectionBean(CategoriaCardapio.class, "descricao", "id", "imagem");

	}

	@Override
	public CategoriaCardapio insert(CategoriaCardapio model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("categorias_cardapio_id_seq"));

		broker.setPropertySQL("categoriacardapiodao.insert", model.getDescricao(), model.getImagem());

		broker.execute();

		return model;

	}

	@Override
	public CategoriaCardapio update(final CategoriaCardapio model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriacardapiodao.update", model.getDescricao(), model.getImagem(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriacardapiodao.delete", id);

		broker.execute();

	}

	public List<CategoriaCardapio> getAll(String empresa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriacardapiodao.findallbyempresa", empresa);

		return broker.getCollectionBean(CategoriaCardapio.class, "descricao", "id", "imagem");

	}

}
