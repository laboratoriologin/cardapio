package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.EmpresaCategoriaCardapio;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class EmpresaCategoriaCardapioDAO  implements RestDAO<EmpresaCategoriaCardapio> {

	@Override
	public EmpresaCategoriaCardapio get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresacategoriacardapiodao.get", id);

		return (EmpresaCategoriaCardapio) broker.getObjectBean(EmpresaCategoriaCardapio.class, "id", "categoriaCardapio.id", "empresa.id", "ordem");

	}

	@Override
	public List<EmpresaCategoriaCardapio> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresacategoriacardapiodao.findall");

		return broker.getCollectionBean(EmpresaCategoriaCardapio.class, "id", "categoriaCardapio.id", "empresa.id", "ordem");

	}

	@Override
	public EmpresaCategoriaCardapio insert(EmpresaCategoriaCardapio model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("empresas_categorias_cardapios_id_seq"));

		broker.setPropertySQL("empresacategoriacardapiodao.insert",model.getId(), model.getCategoriaCardapio().getId(), model.getEmpresa().getId(), model.getOrdem());

		broker.execute();

		return model;

	}

	@Override
	public EmpresaCategoriaCardapio update(final EmpresaCategoriaCardapio model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresacategoriacardapiodao.update", model.getId(), model.getCategoriaCardapio().getId(), model.getEmpresa().getId(), model.getOrdem(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresacategoriacardapiodao.delete", id);

		broker.execute();

	}

}
