package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;
import java.util.stream.Collectors;

import com.sun.xml.internal.ws.util.StringUtils;

import br.com.login.cardapio.beachstop.ws.model.AcaoConta;
import br.com.login.cardapio.beachstop.ws.model.Mesa;
import br.com.login.cardapio.beachstop.ws.model.Pedido;
import br.com.login.cardapio.beachstop.ws.model.Setor;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.service.MesaService;
import br.com.login.cardapio.beachstop.ws.util.Constantes;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class AcaoContaDAO  implements RestDAO<AcaoConta> {

	@Override
	public AcaoConta get(Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.get", id);
		
		return (AcaoConta) broker.getObjectBean(AcaoConta.class, "acao.id", "conta.id", "horarioAtendimento", "horarioSolicitacao", "id", "usuario.id", "pedido.id");

	}

	@Override
	public List<AcaoConta> getAll() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.findall");

		return broker.getCollectionBean(AcaoConta.class, "acao.id", "conta.id", "horarioAtendimento", "horarioSolicitacao", "id", "usuario.id", "pedido.id");

	}
	
	public List<AcaoConta> getAllOpen(String setorId) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder(" SELECT AC.ID, AC.CONTA_ID, AC.HORARIO_SOLICITACAO, AC.ACAO_ID, C.CLIENTE_ID, C.DATA_ABERTURA, C.NUMERO, C.QTD_PESSOA, AC.PEDIDO_ID");
					                 sql.append(" FROM ACOES_CONTAS AS AC")
					              .append(" INNER JOIN CONTAS AS C ON C.ID = AC.CONTA_ID")
					                   .append(" WHERE AC.USUARIO_ID IS NULL ")
					                     .append(" AND AC.HORARIO_ATENDIMENTO IS NULL ")
					                     .append(" AND DATA_FECHAMENTO IS NULL")
					                     .append(" AND C.NUMERO IN (SELECT NUMERO FROM MESAS");
			                    		 
					                    		 if("0".equals(setorId)){
					                    			 sql.append(")");
				                    		 	 }else{
				                    		 		 sql.append(" WHERE SETOR_ID IN ( ").append(setorId).append(" ))");
				                    		 	 }

		broker.setSQL(sql.toString());
		
		List<AcaoConta> list = broker.getCollectionBean(AcaoConta.class, "id", "conta.id", "horarioSolicitacao", "acao.id", "conta.cliente.id", "conta.dataAbertura", "conta.numero", "conta.qtdPessoa", "pedido.id");
		Pedido pedido;
		Status status;
		
		for (AcaoConta acaoConta : list) {
			if(Constantes.Acoes.NovoPedido.equals(acaoConta.getAcao().getId())){
				status = new Status(Constantes.StatusPedido.PENDENTE_APROVACAO.toString());
				pedido = new PedidoDAO().get(acaoConta.getPedido().getId());
				pedido.setSubItens(new PedidoSubItemDAO().getAll(pedido, status));
				
				if(pedido.getSubItens().size() == 0)
					list.remove(acaoConta);
				else
					acaoConta.setPedido(pedido);
			}
		}
		
		return list;
	}

	@Override
	public AcaoConta insert(AcaoConta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		model.setId(broker.getSequenceNextValue("dbo.acoes_contas"));
		
		StringBuilder sql = new StringBuilder("INSERT INTO ACOES_CONTAS (ACAO_ID, CONTA_ID, HORARIO_ATENDIMENTO, HORARIO_SOLICITACAO, USUARIO_ID, PEDIDO_ID) VALUES ( ?, ?,");
		
		if(model.getUsuario().getId() != null)
			sql.append(" GETDATE() ");
		else
			sql.append(" NULL ");
		
		sql.append(", GETDATE(), ?, ?)");

		broker.setSQL(sql.toString(), model.getAcao().getId(), model.getConta().getId(), model.getUsuario().getId(), model.getPedido().getId());

		broker.execute();

		return model;

	}

	@Override
	public AcaoConta update(final AcaoConta model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.update", model.getAcao().getId(), model.getConta().getId(), model.getHorarioAtendimento(), model.getHorarioSolicitacao(), model.getUsuario().getId(), model.getPedido().getId(), model.getId());

		broker.execute();

		return model;

	}
	
	public int finalizarAcaoConta(final Long usuarioId, Long acaoContaId) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		StringBuilder sql = new StringBuilder("UPDATE ACOES_CONTAS SET HORARIO_ATENDIMENTO = GETDATE(), USUARIO_ID = ? WHERE ID = ?");		
		broker.setSQL(sql.toString(), usuarioId, acaoContaId);
		return broker.execute();
	}

	@Override
	public void delete(Long id) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("acaocontadao.delete", id);

		broker.execute();

	}

	public AcaoConta updateResolverAcao(final AcaoConta model) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("acaocontadao.updateresolveracao", model.getUsuario().getId(), model.getId());
		broker.execute();
		return model;		
	}
}
