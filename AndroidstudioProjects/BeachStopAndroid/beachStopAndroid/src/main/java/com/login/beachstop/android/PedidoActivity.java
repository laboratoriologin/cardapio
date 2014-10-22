package com.login.beachstop.android;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.login.beachstop.android.adapter.ViewPagerAdapter;
import com.login.beachstop.android.fragment.ITabFragmentPedido;
import com.login.beachstop.android.fragment.TabFragmentConta;
import com.login.beachstop.android.fragment.TabFragmentPedido;
import com.login.beachstop.android.view.ActionBar;

public class PedidoActivity extends DefaultActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, PedidoActivity.TabInfo>();
	private ViewPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);
		((ImageView) (findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar)).setBackgroundResource(R.drawable.tab_pedido);
		((TextView) (findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText("Meus pedidos");

		startTab(null);
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

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, TabFragmentPedido.class.getName()));
		fragments.add(Fragment.instantiate(this, TabFragmentConta.class.getName()));

		this.mPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager(), fragments);
		//
		this.mViewPager = (ViewPager) findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * Initialise the Tab Host
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;

		View tabviewGrid = createTabView(mTabHost.getContext(), "Pedido");
		View tabviewList = createTabView(mTabHost.getContext(), "Conta");

		PedidoActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Galeria").setIndicator(tabviewGrid), (tabInfo = new TabInfo("Pedido", TabFragmentPedido.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		PedidoActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Lista").setIndicator(tabviewList), (tabInfo = new TabInfo("Conta", TabFragmentConta.class, args)));
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
	private static void AddTab(PedidoActivity pedidoActivity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		tabSpec.setContent(pedidoActivity.new TabFactory(pedidoActivity));
		tabHost.addTab(tabSpec);

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg_pedido, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

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
		((ITabFragmentPedido) mPagerAdapter.getItem(position)).onRefresh();
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
