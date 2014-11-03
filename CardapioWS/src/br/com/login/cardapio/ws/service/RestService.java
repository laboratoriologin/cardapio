<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/service/RestService.java
package br.com.login.cardapio.beachstop.ws.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.apache.commons.beanutils.BeanUtils;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.beachstop.ws.dao.RestDAO;
import br.com.login.cardapio.beachstop.ws.exception.ApplicationException;
import br.com.login.cardapio.beachstop.ws.model.RestModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

public abstract class RestService<T extends RestModel> {

	protected RestDAO<T> restDAO;

	private final String ID = "id";

	public RestService() {
		this.initDAO();
	}

	@GET
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public List<T> getAll() {
		return restDAO.getAll();
	}

	@GET
	@Path("/{id}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public T get(@PathParam(ID) Long id, @PathParam("fields") String fields) {

		T object = restDAO.get(id);

		if (fields != null && !"".equals(fields)) {

			this.configureReturnObject(object, fields);

		}

		return object;

	}

	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public T insert(@Form T form) throws ApplicationException {
		try {

			this.validate(form);

			return restDAO.insert(form);

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	@PUT
	@Path("/{id}")
	@Produces("application/json; charset=UTF-8")
	public T update(@Form T form, @PathParam("id") Long id) throws ApplicationException {

		try {

			this.validate(form);

			form.setId(id);

			return restDAO.update(form);

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Long id) throws ApplicationException {
		try {
			restDAO.delete(id);
		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	public abstract void initDAO();

	protected void validate(T object) throws ApplicationException {
	}

	protected void configureReturnObject(T object, String fields) {

		if (object != null && fields != null && !"".equals(fields)) {

			List<String> filteringFields = Arrays.asList(fields.split("/"));

			if (filteringFields.size() > 0) {

				if (!fields.contains(ID)) {

					object.setId(null);
				}

				for (Field classField : object.getClass().getDeclaredFields()) {

					if (!fields.contains(classField.getName())) {

						try {

							BeanUtils.setProperty(object, classField.getName(), null);

						} catch (Exception e) {
						}

					}

				}

			}

		}

	}

	protected void configureReturnObjects(List<T> objects, String fields) {

		for (T object : objects) {

			this.configureReturnObject(object, fields);

		}

	}

=======
package br.com.login.cardapio.ws.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.catalina.connector.Response;
import org.apache.commons.beanutils.BeanUtils;
import org.jboss.resteasy.annotations.Form;

import br.com.login.cardapio.ws.dao.RestDAO;
import br.com.login.cardapio.ws.exception.ApplicationException;
import br.com.login.cardapio.ws.model.RestModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

public abstract class RestService<T extends RestModel> {

	protected RestDAO<T> restDAO;

	private final String ID = "id";

	public RestService() {
		this.initDAO();
	}

	@GET
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public List<T> getAll() {
		return restDAO.getAll();
	}

	@GET
	@Path("/{id}{fields : (/.*?)?}")
	@Produces("application/json; charset=UTF-8")
	public T get(@PathParam(ID) Long id, @PathParam("fields") String fields) {

		T object = restDAO.get(id);

		this.configureReturnObject(object, fields);

		return object;

	}

	@POST
	@Path("")
	@Produces("application/json; charset=UTF-8")
	public T insert(@Form T form) throws ApplicationException {
		try {

			this.validate(form);

			return restDAO.insert(form);

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	@PUT
	@Path("/{id}")
	@Produces("application/json; charset=UTF-8")
	public T update(@Form T form, @PathParam("id") Long id) throws ApplicationException {

		try {

			form.setId(id);

			this.validate(form);

			return restDAO.update(form);

		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}

	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Long id) throws ApplicationException {
		try {
			restDAO.delete(id);
		} catch (TSApplicationException ex) {

			throw new ApplicationException(ex.getMessage(), Response.SC_BAD_REQUEST);

		} catch (TSSystemException ex) {

			ex.printStackTrace();
			throw new ApplicationException(ex.getMessage(), Response.SC_INTERNAL_SERVER_ERROR);

		}
	}

	public abstract void initDAO();

	protected void validate(T object) throws ApplicationException {
	}

	protected void configureReturnObject(T object, String fields) {

		if (object!=null && fields != null && !"".equals(fields)) {

			List<String> filteringFields = Arrays.asList(fields.split("/"));

			if (filteringFields.size() > 0) {

				if (!fields.contains(ID)) {

					object.setId(null);
				}

				for (Field classField : object.getClass().getDeclaredFields()) {

					if (!fields.contains(classField.getName())) {

						try {

							BeanUtils.setProperty(object, classField.getName(), null);

						} catch (Exception e) {
						}

					}

				}

			}

		}

	}

	protected void configureReturnObjects(List<T> objects, String fields) {

		for (T object : objects) {

			this.configureReturnObject(object, fields);

		}

	}

>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/service/RestService.java
}