package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Categoria;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class CategoriaDAO  implements RestDAO<Categoria> {

	@Override
	public Categoria get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriadao.get", id);

		return (Categoria) broker.getObjectBean(Categoria.class, "area.id", "descricao", "flagAtivo", "id", "imagem", "ordem");

	}

	@Override
	public List<Categoria> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriadao.findall");

		return broker.getCollectionBean(Categoria.class, "area.id", "descricao", "flagAtivo", "id", "imagem", "ordem");

	}

	@Override
	public Categoria insert(Categoria model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.categorias "));

		broker.setPropertySQL("categoriadao.insert",model.getArea().getId(), model.getDescricao(), model.getFlagAtivo(), model.getImagem(), model.getOrdem());

		broker.execute();

		return model;

	}

	@Override
	public Categoria update(final Categoria model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriadao.update", model.getArea().getId(), model.getDescricao(), model.getFlagAtivo(), model.getImagem(), model.getOrdem(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("categoriadao.delete", id);

		broker.execute();

	}

}
