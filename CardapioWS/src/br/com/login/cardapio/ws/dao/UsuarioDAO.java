package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Empresa;
import br.com.login.cardapio.ws.model.Usuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class UsuarioDAO implements RestDAO<Usuario> {

	@Override
	public Usuario get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.get", id);

		return (Usuario) broker.getObjectBean(Usuario.class, "email", "empresa.id", "flagAtivo", "id", "login", "nome", "senha");

	}

	@Override
	public List<Usuario> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.findall");

		return broker.getCollectionBean(Usuario.class, "email", "empresa.id", "flagAtivo", "id", "login", "nome", "senha");

	}
	
	public List<Usuario> getAll(Empresa empresa) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.findallbyempresa", empresa.getKeyMobile());

		return broker.getCollectionBean(Usuario.class, "email", "empresa.id", "flagAtivo", "id", "login", "nome");

	}

	@Override
	public Usuario insert(Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("usuarios_id_seq"));

		broker.setPropertySQL("usuariodao.insert", model.getEmail(), model.getEmpresa().getId(), model.getFlagAtivo(), model.getLogin(), model.getNome(), model.getSenha());

		broker.execute();

		return model;

	}

	@Override
	public Usuario update(final Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.update", model.getEmail(), model.getEmpresa().getId(), model.getFlagAtivo(), model.getLogin(), model.getNome(), model.getSenha(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.delete", id);

		broker.execute();

	}

	public Usuario login(Usuario usuario) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.login", usuario.getLogin(), usuario.getSenha(), usuario.getEmpresa().getKeyMobile());

		return (Usuario) broker.getObjectBean(Usuario.class, "email", "empresa.id", "flagAtivo", "id", "login", "nome", "senha");

	}

	public Usuario getByEmail(Usuario usuario) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.getbyemail", usuario.getEmail(), usuario.getEmpresa().getKeyMobile());

		return (Usuario) broker.getObjectBean(Usuario.class, "email", "empresa.id", "flagAtivo", "id", "login", "nome", "senha");

	}

	public void alterarSenha(Usuario form) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.alterarsenha", form.getSenha(), form.getId());

		broker.execute();
	}

}
