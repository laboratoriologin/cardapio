package com.login.beachstop.android.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.ItemRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.DrawableManager;
import com.login.beachstop.android.utils.LoadImage;
import com.login.beachstop.android.views.actionbar.ActionBar;
import com.login.beachstop.android.views.adapters.ViewPagerFragmentAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemFragment extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private View view;
    private CardapioActivity cardapioActivity;
    private Categoria categoria;
    private ImageView imageViewTopoActionBar;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ItemFragment.TabInfo>();
    private ViewPagerFragmentAdapter mPagerAdapter;
    private ProgressBar progressbar;
    private ResponseListener listenerGetItem = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        cardapioActivity.getDataManager().getItemDAO().save((List<Item>) serverResponse.getReturnObject());

                        startTab(null);
                        setVisibility(true);

                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(cardapioActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(cardapioActivity, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                Toast.makeText(cardapioActivity, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }
    };

    /**
     * Add Tab content to the Tabhost
     *
     * @param activity
     * @param tabHost
     * @param tabSpec
     * @param clss
     * @param args
     */
    private static void AddTab(ItemFragment itemFragment, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(itemFragment.new TabFactory(itemFragment.view.getContext()));
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.cardapioActivity = (CardapioActivity) activity;
        Bundle args = getArguments();
        this.categoria = (Categoria) args.getSerializable(Constantes.ARG_CATEGORIA_CARDAPIO);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_item, container, false);

        ((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
        this.imageViewTopoActionBar = (ImageView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar);


        Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + "topo_" + categoria.getImagem());

        if (img == null) {

            new LoadImage(this.imageViewTopoActionBar, cardapioActivity).execute(Constantes.URL_IMG + categoria.getImagem());

        } else {

            this.imageViewTopoActionBar.setImageDrawable(img);

        }

        ((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText(this.categoria.getDescricao());

        this.progressbar = (ProgressBar) this.view.findViewById(R.id.progressBar);

        if (this.cardapioActivity.getDataManager().getItemDAO().getQtdItem() == 0) {

            new ItemRequest(listenerGetItem).getItemByCategorias(this.categoria.getId());

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
        argLista.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, this.categoria);

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(view.getContext(), ItemGridFragment.class.getName(), argLista));
        fragments.add(Fragment.instantiate(view.getContext(), ItemListFragment.class.getName(), argLista));

        this.mPagerAdapter = new ViewPagerFragmentAdapter(this.getChildFragmentManager(), fragments);
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

        ItemFragment.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Galeria").setIndicator(tabviewGrid), (tabInfo = new TabInfo("Galeria", ItemGridFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ItemFragment.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Lista").setIndicator(tabviewList), (tabInfo = new TabInfo("Lista", ItemListFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        mTabHost.setOnTabChangedListener(this);
    }

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
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }

    /**
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
    private class TabFactory implements TabContentFactory {

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