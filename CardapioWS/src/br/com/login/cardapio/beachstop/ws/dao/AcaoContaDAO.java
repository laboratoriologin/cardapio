package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class AcaoContaDAO  implements RestDAO<AcaoConta> {

	@Override
	public AcaoConta get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.get", id);

		return (AcaoConta) broker.getObjectBean(AcaoConta.class, "acao.id", "conta.id", "hararioAtendimento", "horarioSolocitacao", "id", "usuario.id");

	}

	@Override
	public List<AcaoConta> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.findall");

		return broker.getCollectionBean(AcaoConta.class, "acao.id", "conta.id", "hararioAtendimento", "horarioSolocitacao", "id", "usuario.id");

	}

	@Override
	public AcaoConta insert(AcaoConta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.acoes_contas "));

		broker.setPropertySQL("acaocontadao.insert",model.getAcao().getId(), model.getConta().getId(), model.getHararioAtendimento(), model.getHorarioSolocitacao(), model.getUsuario().getId());

		broker.execute();

		return model;

	}

	@Override
	public AcaoConta update(final AcaoConta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.update", model.getAcao().getId(), model.getConta().getId(), model.getHararioAtendimento(), model.getHorarioSolocitacao(), model.getUsuario().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.delete", id);

		broker.execute();

	}

}
