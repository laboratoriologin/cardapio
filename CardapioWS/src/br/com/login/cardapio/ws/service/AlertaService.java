package br.com.login.cardapio.ws.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.login.cardapio.ws.dao.AlertaDAO;
import br.com.login.cardapio.ws.dao.ContaDAO;
import br.com.login.cardapio.ws.dao.EmpresaDAO;
import br.com.login.cardapio.ws.dao.MetricaDAO;
import br.com.login.cardapio.ws.model.Alerta;
import br.com.login.cardapio.ws.model.Conta;
import br.com.login.cardapio.ws.model.Empresa;
import br.com.login.cardapio.ws.model.Metrica;
import br.com.login.cardapio.ws.model.Pedido;
import br.com.login.cardapio.ws.model.PedidosAlertas;
import br.com.login.cardapio.ws.util.Constantes;

@Path("/alertas")
public class AlertaService extends RestService<Alerta> {

	private static final String ALERTA_VERMELHO = "vermelho";

	private static final String ALERTA_AMARELO = "amarelo";

	private Metrica metricaConta;

	private Metrica metricaGarcom;

	private Metrica metricaPedido;

	@Override
	public void initDAO() {
		this.restDAO = new AlertaDAO();
	}

	@GET
	@Path("/empresa/{empresa}/mesa/{n_mesa}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public PedidosAlertas get(@PathParam("empresa") String keyMobile, @PathParam("n_mesa") String nMesa, @PathParam("fields") String fields) {

		PedidosAlertas itens = new PedidosAlertas();

		Empresa empresa = new Empresa();

		empresa.setKeyMobile(keyMobile);

		List<Metrica> metricas = new MetricaDAO().getAll(empresa);

		for (Metrica metrica : metricas) {

			if (Constantes.TIPO_ALERTA_GARCOM.equals(metrica.getTipoAlerta().getId())) {

				metricaGarcom = metrica;

			}

			if (Constantes.TIPO_ALERTA_CONTA.equals(metrica.getTipoAlerta().getId())) {

				metricaConta = metrica;

			}

			if (Constantes.TIPO_ALERTA_PEDIDOS.equals(metrica.getTipoAlerta().getId())) {

				metricaPedido = metrica;

			}

		}

		itens.setAlertas((new AlertaDAO().getAllOpen(empresa, nMesa)));

		for (Alerta alerta : itens.getAlertas()) {

			this.calculateMetrica(alerta);

		}

		itens.setContas(new ContaDAO().getNovosPedidosByEmpresaMesa(keyMobile, nMesa));

		for (Conta conta : itens.getContas()) {

			this.calculateMetrica(conta);

		}

		return itens;

	}

	private void calculateMetrica(Alerta alerta) {

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		long metricaInMinutes = 0;

		long metadeMetricaInMinutes = 0;

		if (Constantes.TIPO_ALERTA_GARCOM.equals(alerta.getTipoAlerta().getId())) {

			metricaInMinutes = Long.valueOf(metricaGarcom.getValor());

			metadeMetricaInMinutes = Long.valueOf(metricaGarcom.getValor()) / 2;

		} else {

			metricaInMinutes = Long.valueOf(metricaConta.getValor());

			metadeMetricaInMinutes = Long.valueOf(metricaConta.getValor()) / 2;

		}

		try {

			final long now = System.currentTimeMillis();

			final long pendingTimeInMinutes = (now - dateformat.parse(alerta.getHorario()).getTime()) / 1000 / 60;

			if (pendingTimeInMinutes > metadeMetricaInMinutes && pendingTimeInMinutes < metricaInMinutes) {

				alerta.setCorMetrica(ALERTA_AMARELO);

			} else if (pendingTimeInMinutes >= metricaInMinutes) {

				alerta.setCorMetrica(ALERTA_VERMELHO);

			}

		} catch (ParseException e) {

			e.printStackTrace();
		}

	}

	private void calculateMetrica(Conta conta) {

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		long metricaInMinutes = Long.valueOf(metricaPedido.getValor());

		long metadeMetricaInMinutes = Long.valueOf(metricaPedido.getValor()) / 2;

		final long now = System.currentTimeMillis();

		long pendingTimeInMinutes = 0;

		for (Pedido pedido : conta.getPedido()) {

			try {

				pendingTimeInMinutes = (now - dateformat.parse(pedido.getHorario()).getTime()) / 1000 / 60;

				if (pendingTimeInMinutes > metadeMetricaInMinutes && pendingTimeInMinutes < metricaInMinutes) {

					pedido.setCorMetrica(ALERTA_AMARELO);

				} else if (pendingTimeInMinutes >= metricaInMinutes) {

					pedido.setCorMetrica(ALERTA_VERMELHO);

					break;

				}

			} catch (ParseException e) {

				e.printStackTrace();

			}

		}
	}

	@GET
	@Path("pedido/empresa/{empresa}/log/{log}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public PedidosAlertas getPedidos(@PathParam("empresa") String keyMobile, @PathParam("log") String log, @PathParam("fields") String fields) {

		Boolean booLog = Boolean.valueOf(log);
		PedidosAlertas itens = new PedidosAlertas();

		Empresa empresa = new EmpresaDAO().get(keyMobile);

		StringBuilder nMesa = new StringBuilder();

		for (int i = 1; i < empresa.getQtdMesa(); i++) {
			nMesa.append(i + ",");
		}

		nMesa.deleteCharAt(nMesa.length() - 1);

		itens.setContas(new ContaDAO().getPedidosNaoEntregueCancelado(keyMobile, nMesa.toString(), booLog));

		return itens;
	}

}
