package com.login.beachstop.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Publicidade;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.PublicidadeRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.actionbar.ActionBar;
import com.login.beachstop.android.views.adapters.PublicidadeFragmentAdapter;
import com.viewpagerindicator.PageIndicator;

import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class CardapioFragment extends Fragment {

    private static final int TIME_SLIDE = 10000;
    private View view;
    private CardapioActivity activity;
    private List<Publicidade> publicidades = null;
    private ViewPager viewPagerMidia = null;
    private PageIndicator mIndicator = null;
    private PublicidadeFragmentAdapter publicidadeFragmentAdapter = null;
    private GridView gridViewMenu;
    private GridItensItemCardapioAdapter gridItensItemMenuAdapter;
    private Handler handler;
    private Runnable runnable;
    private int position;
    private ResponseListener listenerGetPublicidade = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        publicidades = (List<Publicidade>) serverResponse.getReturnObject();

                        if (publicidades.size() != 0) {

                            publicidadeFragmentAdapter = new PublicidadeFragmentAdapter(activity, CardapioFragment.this.getChildFragmentManager(), publicidades);
                            viewPagerMidia.setAdapter(publicidadeFragmentAdapter);
                            mIndicator.setViewPager(viewPagerMidia);

                            position = 0;

                            handler = new Handler();
                            runnable = new Runnable() {

                                public void run() {

                                    if ((position + 1) >= publicidades.size()) {

                                        position = 0;

                                    } else {

                                        position = position + 1;

                                    }

                                    viewPagerMidia.setCurrentItem(position, true);
                                    mIndicator.setCurrentItem(position);
                                    handler.postDelayed(runnable, TIME_SLIDE);
                                }

                            };

                            handler.postDelayed(runnable, TIME_SLIDE);

                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(activity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();

                    }


                } else {

                    Toast.makeText(activity, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                Toast.makeText(activity, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.view = inflater.inflate(R.layout.fragment_cardapio, container, false);
        ((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.FALSE);

        this.viewPagerMidia = (ViewPager) this.view.findViewById(R.id.fragment_cardapio_view_pager_midia);
        this.mIndicator = (PageIndicator) this.view.findViewById(R.id.fragment_cardapio_view_pager_midia_indicator);
        this.gridViewMenu = (GridView) this.view.findViewById(R.id.fragment_cardapio_grid_view_menu);

        new PublicidadeRequest(listenerGetPublicidade).get(null);

        this.gridItensItemMenuAdapter = new GridItensItemCardapioAdapter(this.activity, this.activity.getListaItemCardapio(), new CatagoriaCardapio());
        this.gridViewMenu.setAdapter(gridItensItemMenuAdapter);

        this.gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        this.viewPagerMidia.setOnTouchListener(new View.OnTouchListener() {

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
        this.activity = (CardapioActivity) activity;
    }

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
            //handler.postDelayed(runnable, TIME_SLIDE);
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
