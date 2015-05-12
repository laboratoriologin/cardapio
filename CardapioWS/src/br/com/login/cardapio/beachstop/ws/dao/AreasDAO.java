package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Area;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class AreasDAO  implements RestDAO<Area> {

	@Override
	public Area get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("areasdao.get", id);

		return (Area) broker.getObjectBean(Area.class, "descricao", "id");

	}
	
	public Area getByGrupoUsuario(Long grupoUsuarioId){

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("areasdao.getbygrupousuario", grupoUsuarioId);

		return (Area) broker.getObjectBean(Area.class, "descricao", "id");

	}

	@Override
	public List<Area> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("areasdao.findall");

		return broker.getCollectionBean(Area.class, "descricao", "id");

	}

	@Override
	public Area insert(Area model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.areas "));

		broker.setPropertySQL("areasdao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public Area update(final Area model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("areasdao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("areasdao.delete", id);

		broker.execute();

	}

}
