package br.com.login.cardapio.beachstop.ws.dao;

import java.util.List;

import br.com.topsys.exception.TSApplicationException;
import br.com.login.cardapio.beachstop.ws.model.RestModel;
public interface RestDAO<T extends RestModel> {

	public T insert(T object) throws TSApplicationException;
	public T get(Long id);
	public List<T> getAll();
	public T update(T object) throws TSApplicationException;
	public void delete(Long id) throws TSApplicationException;

}
