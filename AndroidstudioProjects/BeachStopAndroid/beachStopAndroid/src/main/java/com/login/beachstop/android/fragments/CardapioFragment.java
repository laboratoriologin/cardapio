package com.login.beachstop.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.DefaultActivity;
import com.login.beachstop.android.MainActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.SplashActivity;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Publicidade;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.PublicidadeRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.actionbar.ActionBar;
import com.login.beachstop.android.views.adapters.CategoriaGridAdapter;
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
    private List<Publicidade> publicidades = null;
    private List<Categoria> categorias;
    private ViewPager viewPagerMidia = null;
    private PageIndicator mIndicator = null;
    private PublicidadeFragmentAdapter publicidadeFragmentAdapter = null;
    private GridView gridViewMenu;
    private CategoriaGridAdapter categoriaGridAdapter;
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

                            publicidadeFragmentAdapter = new PublicidadeFragmentAdapter(getActivity(), CardapioFragment.this.getChildFragmentManager(), publicidades);
                            viewPagerMidia.setAdapter(publicidadeFragmentAdapter);
                            mIndicator.setViewPager(viewPagerMidia);

                            position = 0;

                            handler = new Handler();
                            runnable = new Runnable() {

                                public void run() {

                                    if ((position + 1) >= publicidades.size()) {

                                        position = 0;

                                    } else {

                                        position += 1;

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
                        Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();

                    }


                } else {

                    Toast.makeText(getActivity(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                Toast.makeText(getActivity(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

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

        TextView textView = (TextView) this.view.findViewById(R.id.text_view_sair);

        textView.setClickable(true);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ((CardapioActivity) getActivity()).getDataManager().getClienteDAO().deleteAll();

                Intent mainIntent = new Intent(getActivity(), SplashActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(mainIntent);
                getActivity().finish();

            }

        });

        new PublicidadeRequest(listenerGetPublicidade).get(new Publicidade());

        this.categorias = ((CardapioActivity) getActivity()).getDataManager().getCategoriaDAO().getAllOrderByOrdem();

        this.categoriaGridAdapter = new CategoriaGridAdapter((DefaultActivity) getActivity(), this.categorias);
        this.gridViewMenu.setAdapter(this.categoriaGridAdapter);

        this.gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ImageView imageView = (ImageView) v.findViewById(R.id.adapter_grid_view_categoria_image_view);
                Categoria categoria = (Categoria) imageView.getTag();

                if (Constantes.TipoCategoriaCardapio.ITEM == categoria.getTipoCategoria()) {

                    goToItemFragment(categoria);

                } else if (Constantes.TipoCategoriaCardapio.TODOS == categoria.getTipoCategoria()) {

                    goToFragmentListaTodosItemCardapio(categoria);

                } else if (Constantes.TipoCategoriaCardapio.KIT == categoria.getTipoCategoria()) {

                    goToItemFragment(categoria);

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

    private void goToFragmentListaTodosItemCardapio(Categoria categoria) {

        ItemAllFragment newFragment = new ItemAllFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, categoria);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activity_cardapio_fragment_layout, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

     public void goToItemFragment(Categoria categoria) {

        ItemFragment newFragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constantes.ARG_CATEGORIA_CARDAPIO, categoria);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activity_cardapio_fragment_layout, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
                Toast.makeText(getActivity(), "Compartilhado!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();

        }
    }

}
