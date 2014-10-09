package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Pagamento;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class PagamentoDAO  implements RestDAO<Pagamento> {

	@Override
	public Pagamento get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pagamentodao.get", id);

		return (Pagamento) broker.getObjectBean(Pagamento.class, "conta.id", "data", "id", "tipoPagamento.id");

	}

	@Override
	public List<Pagamento> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pagamentodao.findall");

		return broker.getCollectionBean(Pagamento.class, "conta.id", "data", "id", "tipoPagamento.id");

	}

	@Override
	public Pagamento insert(Pagamento model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.pagamentos "));

		broker.setPropertySQL("pagamentodao.insert",model.getConta().getId(), model.getData(), model.getTipoPagamento().getId());

		broker.execute();

		return model;

	}

	@Override
	public Pagamento update(final Pagamento model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pagamentodao.update", model.getConta().getId(), model.getData(), model.getTipoPagamento().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("pagamentodao.delete", id);

		broker.execute();

	}

}
