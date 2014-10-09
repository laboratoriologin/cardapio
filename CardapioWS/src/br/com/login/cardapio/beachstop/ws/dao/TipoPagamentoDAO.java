package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.TipoPagamento;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class TipoPagamentoDAO  implements RestDAO<TipoPagamento> {

	@Override
	public TipoPagamento get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopagamentodao.get", id);

		return (TipoPagamento) broker.getObjectBean(TipoPagamento.class, "descricao", "id");

	}

	@Override
	public List<TipoPagamento> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopagamentodao.findall");

		return broker.getCollectionBean(TipoPagamento.class, "descricao", "id");

	}

	@Override
	public TipoPagamento insert(TipoPagamento model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("dbo.tipos_pagamentos "));

		broker.setPropertySQL("tipopagamentodao.insert",model.getDescricao());

		broker.execute();

		return model;

	}

	@Override
	public TipoPagamento update(final TipoPagamento model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopagamentodao.update", model.getDescricao(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tipopagamentodao.delete", id);

		broker.execute();

	}

}
