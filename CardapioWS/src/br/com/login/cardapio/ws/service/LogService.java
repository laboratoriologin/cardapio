<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/LogService.java
package br.com.login.cardapio.beachstop.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.beachstop.ws.model.Log;
import br.com.login.cardapio.beachstop.ws.dao.LogDAO;
@Path("/logs")
public class LogService extends RestService<Log> {

	@Override
	public void initDAO() {
		this.restDAO = new LogDAO();
	}

}
=======
package br.com.login.cardapio.ws.service;

import javax.ws.rs.Path;
import br.com.login.cardapio.ws.model.Log;
import br.com.login.cardapio.ws.dao.LogDAO;
@Path("/logs")
public class LogService extends RestService<Log> {

	@Override
	public void initDAO() {
		this.restDAO = new LogDAO();
	}

}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/LogService.java
