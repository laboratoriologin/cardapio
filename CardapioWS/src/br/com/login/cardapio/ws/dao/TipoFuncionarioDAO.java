<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/dao/TipoUsuarioDAO.java
package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.TipoUsuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoUsuarioDAO  implements RestDAO<TipoUsuario> {

	@Override
	public TipoUsuario get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.get", id);

		return (TipoUsuario) broker.getObjectBean(TipoUsuario.class, "descricao", "id");

	}

	@Override
	public List<TipoUsuario> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.findall");

		return broker.getCollectionBean(TipoUsuario.class, "descricao", "id");

	}

	@Override
	public TipoUsuario insert(TipoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.tipos_usuarios "));

		broker.setPropertySQL("tipousuariodao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public TipoUsuario update(final TipoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipousuariodao.delete", id);

		broker.execute();

	}

}
=======
package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.TipoUsuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoFuncionarioDAO  implements RestDAO<TipoUsuario> {

	@Override
	public TipoUsuario get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipofuncionariodao.get", id);

		return (TipoUsuario) broker.getObjectBean(TipoUsuario.class, "descricao", "id");

	}

	@Override
	public List<TipoUsuario> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipofuncionariodao.findall");

		return broker.getCollectionBean(TipoUsuario.class, "descricao", "id");

	}

	@Override
	public TipoUsuario insert(TipoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tipos_funcionarios_id_seq"));

		broker.setPropertySQL("tipofuncionariodao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public TipoUsuario update(final TipoUsuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipofuncionariodao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipofuncionariodao.delete", id);

		broker.execute();

	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/dao/TipoFuncionarioDAO.java
