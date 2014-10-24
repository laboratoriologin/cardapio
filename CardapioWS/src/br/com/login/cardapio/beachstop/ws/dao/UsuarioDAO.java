package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class UsuarioDAO  implements RestDAO<Usuario> {

	@Override
	public Usuario get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.get", id);

		return (Usuario) broker.getObjectBean(Usuario.class, "celular", "contato", "cpf", "dataNascimento", "email", "endereco", "flagAtivo", "grupoUsuario.id", "id", "login", "nome", "rg", "senha", "telefone", "telefoneContato");

	}

	@Override
	public List<Usuario> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.findall");

		return broker.getCollectionBean(Usuario.class, "celular", "contato", "cpf", "dataNascimento", "email", "endereco", "flagAtivo", "grupoUsuario.id", "id", "login", "nome", "rg", "senha", "telefone", "telefoneContato");

	}

	@Override
	public Usuario insert(Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.usuarios "));

		broker.setPropertySQL("usuariodao.insert",model.getCelular(), model.getContato(), model.getCpf(), model.getDataNascimento(), model.getEmail(), model.getEndereco(), model.getFlagAtivo(), model.getGrupoUsuario().getId(), model.getLogin(), model.getNome(), model.getRg(), model.getSenha(), model.getTelefone(), model.getTelefoneContato());

		broker.execute();

		return model;

	}

	@Override
	public Usuario update(final Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.update", model.getCelular(), model.getContato(), model.getCpf(), model.getDataNascimento(), model.getEmail(), model.getEndereco(), model.getFlagAtivo(), model.getGrupoUsuario().getId(), model.getLogin(), model.getNome(), model.getRg(), model.getSenha(), model.getTelefone(), model.getTelefoneContato(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.delete", id);

		broker.execute();

	}

}
