package com.login.beachstop.android.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.adapter.ViewPagerAdapter;
import com.login.beachstop.android.business.BusinessResult;
import com.login.beachstop.android.business.ItemCardapioBS;
import com.login.beachstop.android.exception.PersistException;
import com.login.beachstop.android.model.CategoriaCardapioItemSys;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.view.ActionBar;

public class ListaItemCardapioFragment extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, BusinessResult {

	private View view;
	private HomeActivity homeActivity;

	private CategoriaCardapioItemSys itemMenu;
	private ImageView imageViewTopoActionBar;

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ListaItemCardapioFragment.TabInfo>();
	private ViewPagerAdapter mPagerAdapter;
	private ProgressBar progressbar;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.homeActivity = (HomeActivity) activity;
		Bundle args = getArguments();
		this.itemMenu = (CategoriaCardapioItemSys) args.getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = inflater.inflate(R.layout.fragment_lista_item_cardapio, container, false);

		((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		this.imageViewTopoActionBar = (ImageView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar);
		this.imageViewTopoActionBar.setBackgroundResource(this.itemMenu.getResourceImgTopoCardapio());
		((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText(this.itemMenu.getNome());

		this.progressbar = (ProgressBar) this.view.findViewById(R.id.progressBar);

		if (this.homeActivity.getDataManager().getAll((long) this.itemMenu.getId()).size() == 0) {
			new ItemCardapioBS(this).getCardapioEmpresa(this.itemMenu.getId());
		} else {
			startTab(savedInstanceState);
			setVisibility(true);
		}

		return this.view;
	}

	private void setVisibility(Boolean a) {
		if (a) {
			this.progressbar.setVisibility(ProgressBar.GONE);
			this.mTabHost.setVisibility(ProgressBar.VISIBLE);
		} else {
			this.progressbar.setVisibility(ProgressBar.VISIBLE);
			this.mTabHost.setVisibility(ProgressBar.GONE);
		}
	}

	public void startTab(Bundle savedInstanceState) {
		this.initialiseTabHost(savedInstanceState);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

		// Intialise ViewPager
		this.intialiseViewPager();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	/**
	 * Initialise ViewPager
	 */
	private void intialiseViewPager() {

		Bundle argLista = new Bundle();
		argLista.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, itemMenu);

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(view.getContext(), TabFragmentGridItemCardapio.class.getName(), argLista));
		fragments.add(Fragment.instantiate(view.getContext(), TabFragmentListItemCardapio.class.getName(), argLista));

		this.mPagerAdapter = new ViewPagerAdapter(this.getChildFragmentManager(), fragments);
		//
		this.mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * Initialise the Tab Host
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;

		View tabviewGrid = createTabView(mTabHost.getContext(), "Galeria", R.drawable.icone_galeria);
		View tabviewList = createTabView(mTabHost.getContext(), "Lista", R.drawable.icone_lista);

		ListaItemCardapioFragment.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Galeria").setIndicator(tabviewGrid), (tabInfo = new TabInfo("Galeria", TabFragmentGridItemCardapio.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		ListaItemCardapioFragment.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Lista").setIndicator(tabviewList), (tabInfo = new TabInfo("Lista", TabFragmentListItemCardapio.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);

		mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * Add Tab content to the Tabhost
	 * 
	 * @param activity
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	private static void AddTab(ListaItemCardapioFragment listaItemCardapioFragment, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		tabSpec.setContent(listaItemCardapioFragment.new TabFactory(listaItemCardapioFragment.view.getContext()));
		tabHost.addTab(tabSpec);
	}

	private static View createTabView(final Context context, final String text, final int idImg) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

		ImageView iv = (ImageView) view.findViewById(R.id.tabsImagemView);
		iv.setBackgroundResource(idImg);

		return view;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		// TabInfo newTab = this.mapTabInfo.get(tag);
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		this.mTabHost.setCurrentTab(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void getBusinessResult(Object result) {

		if (result != null) {

			ServerResponse serverResponse = (ServerResponse) result;

			if (serverResponse.isOK()) {

				try {
					for (ItemCardapio itemCardapio : (List<ItemCardapio>) serverResponse.getReturnObject()) {
						this.homeActivity.getDataManager().save(itemCardapio);
					}
					startTab(null);
					setVisibility(true);
				} catch (PersistException e) {
					Toast.makeText(homeActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(homeActivity, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(homeActivity, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * @author mwho Maintains extrinsic info of a tab's construct
	 */
	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.setClss(clazz);
			this.setArgs(args);
		}

		@SuppressWarnings("unused")
		public Class<?> getClss() {
			return clss;
		}

		public void setClss(Class<?> clss) {
			this.clss = clss;
		}

		@SuppressWarnings("unused")
		public Bundle getArgs() {
			return args;
		}

		public void setArgs(Bundle args) {
			this.args = args;
		}

		@SuppressWarnings("unused")
		public Fragment getFragment() {
			return fragment;
		}

		@SuppressWarnings("unused")
		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}

	}

	/**
	 * A simple factory that returns dummy views to the Tabhost
	 * 
	 * @author mwho
	 */
	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */
		public TabFactory(Context context) {
			mContext = context;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		 */
		public View createTabContent(String tag) {
			View v = new View(mContext);

			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

}
