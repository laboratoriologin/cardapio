package br.com.login.cardapio.ws.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

import br.com.login.cardapio.ws.model.UsuariosMesas;

public class ServiceApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public ServiceApplication() {

		this.singletons.add(new AgendaPublicidadeService());

		this.singletons.add(new CategoriaCardapioService());

		this.singletons.add(new ContaService());

		this.singletons.add(new EmpresaService());

		this.singletons.add(new EmpresaCategoriaCardapioService());

		this.singletons.add(new ItemService());

		this.singletons.add(new ItemPedidoService());

		this.singletons.add(new LogService());

		this.singletons.add(new MenuService());

		this.singletons.add(new PedidoService());

		this.singletons.add(new PermissaoService());

		this.singletons.add(new PublicidadeService());

		this.singletons.add(new StatusService());

		this.singletons.add(new SubItemService());

		this.singletons.add(new TipoAgendaService());

		this.singletons.add(new TipoPublicidadeService());

		this.singletons.add(new TipoQuantidadeService());

		this.singletons.add(new TokenService());
		
		this.singletons.add(new UsuarioService());

		this.singletons.add(new AlertaService());

		this.singletons.add(new UsuariosMesasService());

		this.registerConverters();

	}

	private void registerConverters() {

		ConvertUtils.register(new LongConverter(null), Long.class);

		ConvertUtils.register(new DoubleConverter(null), Double.class);

		ConvertUtils.register(new BooleanConverter(null), Boolean.class);

		ConvertUtils.register(new IntegerConverter(null), Integer.class);

		ConvertUtils.register(new DateConverter(null), Date.class);

	}

	@Override
	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return this.singletons;
	}

}