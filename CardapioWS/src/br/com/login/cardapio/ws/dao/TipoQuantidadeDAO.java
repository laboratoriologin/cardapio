package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.TipoQuantidade;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoQuantidadeDAO  implements RestDAO<TipoQuantidade> {

	@Override
	public TipoQuantidade get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoquantidadedao.get", id);

		return (TipoQuantidade) broker.getObjectBean(TipoQuantidade.class, "descricao", "id", "imagem");

	}

	@Override
	public List<TipoQuantidade> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoquantidadedao.findall");

		return broker.getCollectionBean(TipoQuantidade.class, "descricao", "id", "imagem");

	}

	@Override
	public TipoQuantidade insert(TipoQuantidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tipos_quantidades_id_seq"));

		broker.setPropertySQL("tipoquantidadedao.insert",model.getDescricao(), model.getImagem());

		broker.execute();

		return model;

	}

	@Override
	public TipoQuantidade update(final TipoQuantidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoquantidadedao.update", model.getDescricao(), model.getImagem(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipoquantidadedao.delete", id);

		broker.execute();

	}

}
