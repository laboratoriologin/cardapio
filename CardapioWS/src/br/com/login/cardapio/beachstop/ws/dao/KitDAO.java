package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Kit;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class KitDAO  implements RestDAO<Kit> {

	@Override
	public Kit get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitdao.get", id);

		return (Kit) broker.getObjectBean(Kit.class, "desconto", "descricao", "flagAtivo", "id", "nome", "ordem", "imagem");

	}

	@Override
	public List<Kit> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitdao.findall");
		
		KitSubItemDAO kitSubItemDAO = new KitSubItemDAO();

		List<Kit> kits = broker.getCollectionBean(Kit.class, "desconto", "descricao", "flagAtivo", "id", "nome", "ordem", "imagem");
		
		for (Kit kit : kits) {
			kit.setKitSubItens(kitSubItemDAO.getAll(kit));
		}
		
		return kits;

	}

	@Override
	public Kit insert(Kit model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.kits "));

		broker.setPropertySQL("kitdao.insert",model.getDesconto(), model.getDescricao(), model.getFlagAtivo(), model.getNome(), model.getOrdem(), model.getImagem());

		broker.execute();

		return model;

	}

	@Override
	public Kit update(final Kit model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitdao.update", model.getDesconto(), model.getDescricao(), model.getFlagAtivo(), model.getNome(), model.getOrdem(), model.getId(), model.getImagem());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("kitdao.delete", id);

		broker.execute();

	}

}
