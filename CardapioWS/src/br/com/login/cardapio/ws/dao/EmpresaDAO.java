package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.Empresa;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class EmpresaDAO implements RestDAO<Empresa> {

	@Override
	public Empresa get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresadao.get", id);

		return (Empresa) broker.getObjectBean(Empresa.class, "cnpj", "dadosEmpresa", "descricao", "email", "endereco", "id", "imagemLogo", "keyCardapio", "keyMobile", "latitude", "longitude", "nome", "telefone", "qtdMesa");

	}

	public Empresa get(String keyMobile) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresadao.getbykeymobile", keyMobile);

		return (Empresa) broker.getObjectBean(Empresa.class, "cnpj", "dadosEmpresa", "descricao", "email", "endereco", "id", "imagemLogo", "keyCardapio", "keyMobile", "latitude", "longitude", "nome", "telefone", "qtdMesa");

	}

	@Override
	public List<Empresa> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresadao.findall");

		return broker.getCollectionBean(Empresa.class, "cnpj", "dadosEmpresa", "descricao", "email", "endereco", "id", "imagemLogo", "keyCardapio", "keyMobile", "latitude", "longitude", "nome", "telefone", "qtdMesa");

	}

	@Override
	public Empresa insert(Empresa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("empresas_id_seq"));

		broker.setPropertySQL("empresadao.insert", model.getCnpj(), model.getDadosEmpresa(), model.getDescricao(), model.getEmail(), model.getEndereco(), model.getImagemLogo(), model.getKeyCardapio(), model.getKeyMobile(), model.getLatitude(), model.getLongitude(), model.getNome(), model.getTelefone());

		broker.execute();

		return model;

	}

	@Override
	public Empresa update(final Empresa model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresadao.update", model.getCnpj(), model.getDadosEmpresa(), model.getDescricao(), model.getEmail(), model.getEndereco(), model.getImagemLogo(), model.getKeyCardapio(), model.getKeyMobile(), model.getLatitude(), model.getLongitude(), model.getNome(), model.getTelefone(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("empresadao.delete", id);

		broker.execute();

	}

}
