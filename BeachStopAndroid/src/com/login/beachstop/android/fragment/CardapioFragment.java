package com.login.beachstop.android.fragment;

import java.util.List;

import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.adapter.GridItensItemCardapioAdapter;
import com.login.beachstop.android.adapter.PublicidadeFragmentAdapter;
import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.business.PublicidadeBS;
import com.login.beachstop.android.model.CatagoriaCardapio;
import com.login.beachstop.android.model.CategoriaCardapioItemSys;
import com.login.beachstop.android.model.Empresa;
import com.login.beachstop.android.model.Publicidade;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.view.ActionBar;
import com.viewpagerindicator.PageIndicator;

public class CardapioFragment extends Fragment implements BusinessResult {

	private static final int TIME_SLIDE = 10000;
	private View view;
	private HomeActivity activity;
	private List<Publicidade> listPublicidade = null;
	private ViewPager viewPagerMidia = null;
	private PageIndicator mIndicator = null;
	private PublicidadeFragmentAdapter publicidadeFragmentAdapter = null;
	private GridView gridViewMenu;
	private GridItensItemCardapioAdapter gridItensItemMenuAdapter;
	// private SocialAuthAdapter socialAuthAdapter;

	private Handler handler;
	private Runnable runnable;
	private int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.view = inflater.inflate(R.layout.fragment_cardapio, container, false);
		((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);

		this.viewPagerMidia = (ViewPager) this.view.findViewById(R.id.fragment_cardapio_view_pager_midia);
		this.mIndicator = (PageIndicator) this.view.findViewById(R.id.fragment_cardapio_view_pager_midia_indicator);
		this.gridViewMenu = (GridView) this.view.findViewById(R.id.fragment_cardapio_grid_view_menu);

		new PublicidadeBS(this).getPublicidade();

		this.gridItensItemMenuAdapter = new GridItensItemCardapioAdapter(this.activity, this.activity.getListaItemCardapio(), new CatagoriaCardapio());
		this.gridViewMenu.setAdapter(gridItensItemMenuAdapter);

		this.gridViewMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				ImageView imageView = (ImageView) v.findViewById(R.id.grid_view_item_menu_image_view);
				CategoriaCardapioItemSys itemMenu = (CategoriaCardapioItemSys) imageView.getTag();

				if (ListaItemCardapioFragment.class.equals(itemMenu.getClasse())) {
					goToFragmentListaItemCardapio(itemMenu);
				} else if (ListaTodosItemCardapioFragment.class.equals(itemMenu.getClasse())) {
					goToFragmentListaTodosItemCardapio(itemMenu);
				}
			}
		});

		// socialAuthAdapter = new SocialAuthAdapter(new ResponseListener());
		//
		// // Add providers
		// socialAuthAdapter.addProvider(Provider.FACEBOOK,
		// R.drawable.facebook);
		//
		// socialAuthAdapter.enable((Button)
		// this.view.findViewById(R.id.fragment_cardapio_btn_compartilhar_checkin));

		((Button) this.view.findViewById(R.id.fragment_cardapio_btn_como_chegar)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				List<Empresa> listChaves = activity.getDataManager().getChaveCardapioEmpresaDAO().getAll();

				if (listChaves.size() != 0) {
					Empresa empresa = listChaves.get(0);

					Uri gmmIntentUri = Uri.parse("google.navigation:q=" + empresa.getLat() + "," + empresa.getLon());
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					startActivity(mapIntent);
				}
			}
		});

		this.viewPagerMidia.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				position = viewPagerMidia.getCurrentItem();

				return false;
			}
		});

		return view;
	}

	private void goToFragmentListaTodosItemCardapio(CategoriaCardapioItemSys itemMenu) {

		ListaTodosItemCardapioFragment newFragment = new ListaTodosItemCardapioFragment();
		Bundle args = new Bundle();
		args.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, itemMenu);
		newFragment.setArguments(args);
		FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.activity_home_fragment_layout, newFragment);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	public void goToFragmentListaItemCardapio(CategoriaCardapioItemSys itemMenu) {
		// If the frag is not available, we're in the one-pane layout and must
		// swap frags...

		// Create fragment and give it an argument for the selected article
		ListaItemCardapioFragment newFragment = new ListaItemCardapioFragment();
		Bundle args = new Bundle();
		args.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, itemMenu);
		newFragment.setArguments(args);
		FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack so the user can navigate
		// back
		transaction.replace(R.id.activity_home_fragment_layout, newFragment);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (HomeActivity) activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;
		this.listPublicidade = (List<Publicidade>) serverResponse.getReturnObject();

		if (this.listPublicidade.size() != 0) {

			this.publicidadeFragmentAdapter = new PublicidadeFragmentAdapter(this.activity, this.getChildFragmentManager(), this.listPublicidade);
			this.viewPagerMidia.setAdapter(this.publicidadeFragmentAdapter);
			this.mIndicator.setViewPager(this.viewPagerMidia);

			this.position = 0;

			this.handler = new Handler();
			this.runnable = new Runnable() {
				public void run() {

					try {

						if ((position + 1) >= listPublicidade.size()) {
							position = 0;
						} else {
							position = position + 1;
						}
						viewPagerMidia.setCurrentItem(position, true);
						mIndicator.setCurrentItem(position);
						handler.postDelayed(runnable, TIME_SLIDE);
					} catch (Exception ex) {
						// erro inesperado, fragment pode ter sido destruído
					}
				}
			};

			handler.postDelayed(runnable, TIME_SLIDE);

		}

	}

	// private final class ResponseListener implements DialogListener {
	// public void onComplete(Bundle values) {
	//
	// try {
	//
	// socialAuthAdapter.updateStatus("No Beach Stop de Ipitanga!", new
	// MessageListener(), true);
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// Toast.makeText(activity,
	// "Erro ao compartilhar na rede social. Tente novamente mais tarde!",
	// Toast.LENGTH_SHORT).show();
	//
	// }
	// }
	//
	// public void onCancel() {
	// Log.d("ShareButton", "Cancelado");
	// }
	//
	// @Override
	// public void onBack() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onError(SocialAuthError arg0) {
	// Log.d("ShareButton", "Cancelado");
	//
	// }
	// }

	@Override
	public void onPause() {
		super.onPause();
		if (handler != null) {
			handler.removeCallbacks(runnable);
		}
	}

	@Override
	public void onResume() {
		super.onResume(); // Always call the superclass method first
		if (handler != null) {
			// handler.postDelayed(runnable, TIME_SLIDE);
		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {

		public void onError(SocialAuthError e) {
			System.out.println(e.getMessage());
		}

		@Override
		public void onExecute(String arg0, Integer status) {
			// TODO Auto-generated method stubInteger status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(activity, "Compartilhado!", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(activity, "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();

		}
	}
}
