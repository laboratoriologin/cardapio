package br.com.login.cardapio.ws.dao;

import java.util.List;

import br.com.login.cardapio.ws.model.RestModel;
import br.com.topsys.exception.TSApplicationException;
public interface RestDAO<T extends RestModel> {

	public T insert(T object) throws TSApplicationException;
	public T get(Long id);
	public List<T> getAll();
	public T update(T object) throws TSApplicationException;
	public void delete(Long id) throws TSApplicationException;

}

