<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/StatusService.java
package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Status;
import br.com.login.cardapio.beachstop.ws.dao.StatusDAO;
@Path("/status")
public class StatusService extends RestService<Status> {

	@Override
	public void initDAO() {
		this.restDAO = new StatusDAO();
	}

}
=======
package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.Status;
import br.com.login.cardapio.ws.dao.StatusDAO;
@Path("/status")
public class StatusService extends RestService<Status> {

	@Override
	public void initDAO() {
		this.restDAO = new StatusDAO();
	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/StatusService.java
