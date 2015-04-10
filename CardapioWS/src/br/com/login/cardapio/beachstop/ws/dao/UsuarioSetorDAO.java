package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Setor;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.login.cardapio.beachstop.ws.model.UsuarioSetor;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class UsuarioSetorDAO  implements RestDAO<UsuarioSetor> {

	@Override
	public UsuarioSetor get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosetordao.get", id);

		return (UsuarioSetor) broker.getObjectBean(UsuarioSetor.class, "id", "setor.id", "usuario.id");

	}

	@Override
	public List<UsuarioSetor> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosetordao.findall");

		return broker.getCollectionBean(UsuarioSetor.class, "id", "setor.id", "usuario.id");

	}
	
	public List<UsuarioSetor> getAllByUsuario(Usuario usuario) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosetordao.findallbyusuario", usuario.getId());

		return broker.getCollectionBean(UsuarioSetor.class, "id", "setor.id", "usuario.id");

	}

	@Override
	public UsuarioSetor insert(UsuarioSetor model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.usuarios_setores "));

		broker.setPropertySQL("usuariosetordao.insert",model.getSetor().getId(), model.getUsuario().getId());

		broker.execute();

		return model;

	}
	
	public UsuarioSetor insert(UsuarioSetor model, TSDataBaseBrokerIf broker) throws TSApplicationException {
		model.setId(broker.getSequenceNextValue("dbo.usuarios_setores "));
		broker.setPropertySQL("usuariosetordao.insert",model.getSetor().getId(), model.getUsuario().getId());
		broker.execute();
		return model;
	}
	
	public void save(UsuarioSetor model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.beginTransaction();
		
		delete(model.getUsuario().getId(), broker);
		
		UsuarioSetor usuarioSetor;
		
		for (Setor setor : model.getSetores()) {		
			usuarioSetor = new UsuarioSetor();
			usuarioSetor.setSetor(setor);
			usuarioSetor.setUsuario(model.getUsuario());
			
			insert(usuarioSetor, broker);
		}
		broker.endTransaction();
	}

	@Override
	public UsuarioSetor update(final UsuarioSetor model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosetordao.update", model.getSetor().getId(), model.getUsuario().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosetordao.delete", id);

		broker.execute();

	}
	
	public void delete(Long id, TSDataBaseBrokerIf broker) throws TSApplicationException {
		broker.setPropertySQL("usuariosetordao.deletebyusuario", id);
		broker.execute();
	}
}
