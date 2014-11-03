package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Usuario;
import br.com.login.cardapio.ws.model.UsuariosMesas;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class UsuariosMesasDAO implements RestDAO<UsuariosMesas> {

	@Override
	public UsuariosMesas get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosmesasdao.get", id);

		return (UsuariosMesas) broker.getObjectBean(UsuariosMesas.class, "id", "usuario.id", "numeroMesa");
	}

	public List<UsuariosMesas> get(Usuario usuario) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosmesasdao.getbyusuario", usuario.getId());

		return broker.getCollectionBean(UsuariosMesas.class, "id", "usuario.id", "numeroMesa");

	}

	@Override
	public List<UsuariosMesas> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosmesasdao.findall");

		return broker.getCollectionBean(UsuariosMesas.class, "id", "usuario.id", "numeroMesa");

	}
	
	@Override
	public UsuariosMesas insert(UsuariosMesas model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.beginTransaction();
		
		delete(model.getUsuario(), broker);

		if (model.getListNMesa().length() != 0) {			

			for (String item : model.getListNMesa().split(",")) {

				model.setId(broker.getSequenceNextValue("dbo.usuarios_mesas"));

				broker.setPropertySQL("usuariosmesasdao.insert", model.getUsuario().getId(), Long.valueOf(item));

				broker.execute();
			}

		}

		broker.endTransaction();

		return model;

	}

	@Override
	public UsuariosMesas update(final UsuariosMesas model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosmesasdao.update", model.getUsuario().getId(), model.getNumeroMesa(), model.getId());

		broker.execute();

		return model;

	}

	public void delete(Usuario usuario, TSDataBaseBrokerIf broker) throws TSApplicationException {

		broker.setPropertySQL("usuariosmesasdao.deletebyusuario", usuario.getId());

		broker.execute();
	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariosmesasdao.delete", id);

		broker.execute();

	}

}
