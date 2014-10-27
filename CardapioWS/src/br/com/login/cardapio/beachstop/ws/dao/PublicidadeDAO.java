package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Publicidade;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PublicidadeDAO  implements RestDAO<Publicidade> {

	@Override
	public Publicidade get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("publicidadedao.get", id);

		return (Publicidade) broker.getObjectBean(Publicidade.class, "descricao", "id", "imagem", "link", "nome", "tipoPublicidade.id", "vigenciaFinal", "vigenciaInicial");

	}

	@Override
	public List<Publicidade> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("publicidadedao.findall");

		return broker.getCollectionBean(Publicidade.class, "id", "tipoAgenda", "nome", "descricao", "imagem", "link","valor");
		
	}

	@Override
	public Publicidade insert(Publicidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.publicidades "));

		broker.setPropertySQL("publicidadedao.insert",model.getDescricao(), model.getImagem(), model.getLink(), model.getNome(), model.getTipoPublicidade().getId(), model.getVigenciaFinal(), model.getVigenciaInicial());

		broker.execute();

		return model;

	}

	@Override
	public Publicidade update(final Publicidade model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("publicidadedao.update", model.getDescricao(), model.getImagem(), model.getLink(), model.getNome(), model.getTipoPublicidade().getId(), model.getVigenciaFinal(), model.getVigenciaInicial(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("publicidadedao.delete", id);

		broker.execute();

	}

}
