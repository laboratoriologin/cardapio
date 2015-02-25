package com.login.beachstop.android.fragment;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.ItemCardapioSubItem;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.model.Publicidade;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.util.Utilitarios;
import com.login.beachstop.android.util.image.ImageFetcher;
import com.login.beachstop.android.view.ActionBar;

public class PublicidadeDetalheFragment extends Fragment {

	private View view;
	private HomeActivity homeActivity;
	private Publicidade publicidade;
	private ImageView imageView;
	private ImageFetcher mImageFetcher;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.homeActivity = (HomeActivity) activity;
		Bundle args = getArguments();
		publicidade = (Publicidade) args.getSerializable(Constantes.ARG_PUBLICIDADE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = inflater.inflate(R.layout.fragment_detalhe_publicidade, container, false);

		this.mImageFetcher = new ImageFetcher(this.getActivity().getBaseContext(), 500);
		this.mImageFetcher.setLoadingImage(R.drawable.placeholder);
		this.mImageFetcher.addImageCache(this.getActivity(), Constantes.IMAGE_CACHE);

		((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		
		((TextView) this.view.findViewById(R.id.fragment_detalhe_publicidade_text_view_descricao)).setText(publicidade.getDescricao());

		this.imageView = (ImageView) this.view.findViewById(R.id.fragment_detalhe_publicidade_image_view);

		if (publicidade.getImagem().length() != 0) {
			this.mImageFetcher.loadImage(Constantes.URL_IMG + publicidade.getImagem(), imageView);
		}

		return this.view;
	}
}
