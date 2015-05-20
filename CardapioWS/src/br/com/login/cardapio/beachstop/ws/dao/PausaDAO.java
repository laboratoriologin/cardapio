package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Pausa;
import br.com.login.cardapio.beachstop.ws.model.Usuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class PausaDAO implements RestDAO<Pausa> {

	@Override
	public Pausa get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.get", id);

		return (Pausa) broker.getObjectBean(Pausa.class, "horarioFinal", "horarioInicial", "id", "usuario.id");

	}

	public Pausa get(Pausa pausa) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pausadao.getdiff", pausa.getId());
		return (Pausa) broker.getObjectBean(Pausa.class, "id", "usuario.id", "usuario.nome", "strHorarioInicial", "diffMinuto");
	}
	
	public Pausa get(Usuario usuario) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pausadao.getdiffbyusuario", usuario.getId());
		return (Pausa) broker.getObjectBean(Pausa.class, "id", "usuario.id", "usuario.nome", "strHorarioInicial", "diffMinuto");
	}

	public List<Pausa> getEmPausa() {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pausadao.getempausa");
		return broker.getCollectionBean(Pausa.class, "id", "usuario.nome", "strHorarioInicial", "diffMinuto");
	}
	
	public Boolean usuarioPaused(Usuario usuario) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("pausadao.getusuariopaused", usuario.getId());
		Pausa pausa = (Pausa) broker.getObjectBean(Pausa.class, "usuario.id");
		
		return pausa != null;
	}

	@Override
	public List<Pausa> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.findall");

		return broker.getCollectionBean(Pausa.class, "horarioFinal", "horarioInicial", "id", "usuario.id");

	}

	@Override
	public Pausa insert(Pausa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pausas"));

		broker.setPropertySQL("pausadao.insert", model.getUsuario().getId());

		broker.execute();

		return this.get(model);

	}

	@Override
	public Pausa update(final Pausa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.update", model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pausadao.delete", id);

		broker.execute();

	}

}
