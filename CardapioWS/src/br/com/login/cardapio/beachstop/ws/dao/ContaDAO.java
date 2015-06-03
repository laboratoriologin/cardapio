package br.com.login.cardapio.beachstop.ws.dao;

import java.math.BigDecimal;
import java.util.List;

import br.com.login.cardapio.beachstop.ws.model.Conta;
import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.login.cardapio.beachstop.ws.model.PedidoSubItem;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class ContaDAO implements RestDAO<Conta> {

	@Override
	public Conta get(Long id) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.get", id);
		Conta conta = (Conta) broker.getObjectBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");
		if (conta != null) {
			conta.setPedidoSubItens(new PedidoSubItemDAO().getAll(conta));
			conta.setValor(BigDecimal.ZERO);
			for (PedidoSubItem pedidoSubItem : conta.getPedidoSubItens()) {
				conta.setValor(conta.getValor().add(pedidoSubItem.getValorCalculado()));
			}
			conta.setValorPago(new PagamentoDAO().getValorTotalPagoByConta(conta).getValor());
		}
		return conta;
	}
	
	public Boolean isFechado(Conta conta){
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.getfechada", conta.getId());
		Conta contaFechada = (Conta) broker.getObjectBean(Conta.class, "id"); 
		
		return contaFechada == null;
	}

	public Conta getAnalytic(Long id) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.get", id);
		Conta conta = (Conta) broker.getObjectBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");
		return conta;
	}

	public Conta getByNumeroAnalitico(Long numero) {
		Conta conta = getByMesa(numero);
		if (conta != null) {
			conta.setPedidoSubItens(new PedidoSubItemDAO().getAll(conta));
			conta.setValor(BigDecimal.ZERO);
			for (PedidoSubItem pedidoSubItem : conta.getPedidoSubItens()) {
				conta.setValor(conta.getValor().add(pedidoSubItem.getValorCalculado()));
			}
			conta.setValorPago(new PagamentoDAO().getValorTotalPagoByConta(conta).getValor());
		}
		return conta;
	}
	
	public List<Conta> getContaFechadaByMesa(Mesa mesa){	
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.getcontafechadbymesa", mesa.getNumero());
		return broker.getCollectionBean(Conta.class, "id", "strDataFechamento", "valor");		
	}

	public Conta getByMesa(Long numero) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.getbymesa", numero);
		Conta conta = (Conta) broker.getObjectBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");
		return conta;
	}

	public Conta getByNumeroTipoConta(Integer numero, Boolean tipoMesa, Boolean isAnalitico) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.getbynumerotipoconta", numero, tipoMesa);
		Conta conta = (Conta) broker.getObjectBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");
		if (conta != null && isAnalitico) {
			// TODO: Incluir os pedidos e os subitens do pedido para a conta ser
			// carregada de forma analítica

		}
		return conta;
	}

	@Override
	public List<Conta> getAll() {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.findall");
		return broker.getCollectionBean(Conta.class, "cliente.id", "dataAbertura", "dataFechamento", "id", "numero", "qtdPessoa", "tipoConta");
	}

	@Override
	public Conta insert(Conta model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		model.setId(broker.getSequenceNextValue("dbo.contas"));
		broker.setPropertySQL("contadao.insert", model.getCliente().getId(), model.getDataAbertura(), model.getDataFechamento(), model.getNumero(), model.getQtdPessoa(), model.getTipoConta());
		broker.execute();
		return model;
	}

	@Override
	public Conta update(final Conta model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.update", model.getCliente().getId(), model.getDataAbertura(), model.getDataFechamento(), model.getNumero(), model.getQtdPessoa(), model.getTipoConta(), model.getId());
		broker.execute();
		return model;
	}
	
	public Conta updateMesa(final Conta model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.updatemesa", model.getNumero(), model.getId());
		broker.execute();
		return model;
	}	

	@Override
	public void delete(Long id) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.delete", id);
		broker.execute();
	}

	public Conta fecharConta(Conta model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("contadao.updatefecharconta", model.getId());
		broker.execute();
		return model;
	}
	
	public int reabrirConta(Conta model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		StringBuilder sql = new StringBuilder("UPDATE CONTAS SET DATA_FECHAMENTO = NULL WHERE ID = ?");		
		broker.setSQL(sql.toString(), model.getId());
		return broker.execute();
	}
}
