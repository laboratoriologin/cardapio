<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/dao/PermissaoDAO.java
package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Permissao;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PermissaoDAO  implements RestDAO<Permissao> {

	@Override
	public Permissao get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.get", id);

		return (Permissao) broker.getObjectBean(Permissao.class, "flagAlterar", "flagExcluir", "flagInserir", "grupoUsuario.id", "id", "menu.id");

	}

	@Override
	public List<Permissao> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.findall");

		return broker.getCollectionBean(Permissao.class, "flagAlterar", "flagExcluir", "flagInserir", "grupoUsuario.id", "id", "menu.id");

	}

	@Override
	public Permissao insert(Permissao model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.permissoes "));

		broker.setPropertySQL("permissaodao.insert",model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagInserir(), model.getGrupoUsuario().getId(), model.getMenu().getId());

		broker.execute();

		return model;

	}

	@Override
	public Permissao update(final Permissao model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.update", model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagInserir(), model.getGrupoUsuario().getId(), model.getMenu().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.delete", id);

		broker.execute();

	}

}
=======
package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Permissao;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PermissaoDAO  implements RestDAO<Permissao> {

	@Override
	public Permissao get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.get", id);

		return (Permissao) broker.getObjectBean(Permissao.class, "empresa.id", "flagAlterar", "flagExcluir", "flagInserir", "id", "menu.id");

	}

	@Override
	public List<Permissao> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.findall");

		return broker.getCollectionBean(Permissao.class, "empresa.id", "flagAlterar", "flagExcluir", "flagInserir", "id", "menu.id");

	}

	@Override
	public Permissao insert(Permissao model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("permissoes_id_seq"));

		broker.setPropertySQL("permissaodao.insert",model.getEmpresa().getId(), model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagInserir(), model.getMenu().getId());

		broker.execute();

		return model;

	}

	@Override
	public Permissao update(final Permissao model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.update", model.getEmpresa().getId(), model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagInserir(), model.getMenu().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("permissaodao.delete", id);

		broker.execute();

	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/dao/PermissaoDAO.java
