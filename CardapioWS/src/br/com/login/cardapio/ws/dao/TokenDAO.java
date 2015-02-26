package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Token;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class TokenDAO  implements RestDAO<Token> {

	@Override
	public Token get(Long id) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("tokendao.get", id);
		return (Token) broker.getObjectBean(Token.class, "token", "id");
	}

	@Override
	public List<Token> getAll() {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("tokendao.findall");
		return broker.getCollectionBean(Token.class, "token", "id");
	}

	@Override
	public Token insert(Token model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		model.setId(broker.getSequenceNextValue("token_id_seq"));
		broker.setPropertySQL("tokendao.insert",model.getToken());
		broker.execute();
		return model;
	}
	
	public Token insertIPhone(Token model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("tokendao.insertiphone",model.getToken());
		broker.execute();
		return model;
	}

	@Override
	public Token update(final Token model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("tokendao.update", model.getToken(), model.getId());
		broker.execute();
		return model;
	}

	@Override
	public void delete(Long id) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("tokendao.delete", id);
		broker.execute();
	}
}