package br.com.login.cardapio.beachstop.ws.service;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class ServiceApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public ServiceApplication() {

 		this.singletons.add(new AcaoService());

 		this.singletons.add(new AcaoContaService());

 		this.singletons.add(new AgendaPublicidadeService());

 		this.singletons.add(new AreasService());

 		this.singletons.add(new CategoriaService());

 		this.singletons.add(new ClienteService());

 		this.singletons.add(new ContaService());

 		this.singletons.add(new EmpresaService());

 		this.singletons.add(new GrupoUsuarioService());

 		this.singletons.add(new ItemService());

 		this.singletons.add(new KitService());

 		this.singletons.add(new KitSubItemService());

 		this.singletons.add(new LogService());

 		this.singletons.add(new MenuService());

 		this.singletons.add(new MesaService());

 		this.singletons.add(new PagamentoService());

 		this.singletons.add(new PausaService());

 		this.singletons.add(new PedidoService());

 		this.singletons.add(new PedidoSubItemService());

 		this.singletons.add(new PermissaoService());

 		this.singletons.add(new PublicidadeService());

 		this.singletons.add(new SetorService());

 		this.singletons.add(new StatusService());

 		this.singletons.add(new SubItemService());

 		this.singletons.add(new TipoAgendaService());

 		this.singletons.add(new TipoPagamentoService());

 		this.singletons.add(new TipoPublicidadeService());

 		this.singletons.add(new TipoUsuarioService());

 		this.singletons.add(new UsuarioService());

 		this.singletons.add(new UsuarioSetorService());

	}

@	Override
	public Set<Class<?>> getClasses() {
		return this.empty;
	}

@	Override
	public Set<Object> getSingletons() {
		return this.singletons;
	}

}