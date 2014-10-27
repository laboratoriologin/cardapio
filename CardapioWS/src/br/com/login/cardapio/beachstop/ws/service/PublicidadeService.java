package br.com.login.cardapio.beachstop.ws.service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.login.cardapio.beachstop.ws.dao.PublicidadeDAO;
import br.com.login.cardapio.beachstop.ws.model.Publicidade;
@Path("/publicidades")
public class PublicidadeService extends RestService<Publicidade> {
	
	public static final int TIPO_AGENDA_SEMANAL = 2;
	public static final int TIPO_AGENDA_MENSAL  = 3;

	@Override
	public void initDAO() {
		this.restDAO = new PublicidadeDAO();
	}

	@GET
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public Set<Publicidade> getPublicidades() {

		List<Publicidade> publicidades = new PublicidadeDAO().getAll();

		Set<Publicidade> conjuntoPublicidade = new HashSet<Publicidade>();

		Calendar hoje = Calendar.getInstance();

		int mesAtual = hoje.get(Calendar.MONTH) + 1;

		Publicidade publicidade = null;

		for (int contador = 0; contador < publicidades.size(); contador++) {

			publicidade = publicidades.get(contador);

			switch (publicidade.getTipoAgenda().intValue()) {

			case TIPO_AGENDA_SEMANAL: {

				if (hoje.get(Calendar.DAY_OF_WEEK) == publicidade.getValor().intValue()) {

					conjuntoPublicidade.add(publicidade);

				}

			}

			break;

			case TIPO_AGENDA_MENSAL: {

				if (contador != publicidades.size() - 1) {
					
					Publicidade publicidadeFinal = publicidades.get(contador+1);

					if ((publicidade.equals(publicidadeFinal))
							&& publicidade.getValor().intValue() >=mesAtual && publicidadeFinal.getValor() >= mesAtual) {

						conjuntoPublicidade.add(publicidade);

					}

				}

			}
			
			break;

			default:

				conjuntoPublicidade.add(publicidade);

				break;
			}

		}
	
		return conjuntoPublicidade;
	
	}
	
}
