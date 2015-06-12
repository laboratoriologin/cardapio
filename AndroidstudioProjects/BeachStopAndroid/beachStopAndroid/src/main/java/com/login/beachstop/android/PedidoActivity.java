package com.login.beachstop.android;

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

import com.login.beachstop.android.fragments.ContaFragment;
import com.login.beachstop.android.fragments.IPedidoFragment;
import com.login.beachstop.android.fragments.PedidoFragment;
import com.login.beachstop.android.views.actionbar.ActionBar;
import com.login.beachstop.android.views.adapters.ViewPagerFragmentAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Argus on 30/10/2014.
 */
public class PedidoActivity extends DefaultActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, PedidoActivity.TabInfo>();
    private ViewPagerFragmentAdapter mPagerAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        ((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);
        ((findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar)).setBackgroundResource(R.drawable.tab_pedido);
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
        fragments.add(Fragment.instantiate(this, PedidoFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ContaFragment.class.getName()));

        this.mPagerAdapter = new ViewPagerFragmentAdapter(this.getSupportFragmentManager(), fragments);
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

        PedidoActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Galeria").setIndicator(tabviewGrid), (tabInfo = new TabInfo("Pedido", PedidoFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        PedidoActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Lista").setIndicator(tabviewList), (tabInfo = new TabInfo("Conta", ContaFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        mTabHost.setOnTabChangedListener(this);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
        ((IPedidoFragment) mPagerAdapter.getItem(position)).onRefresh();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub
    }

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