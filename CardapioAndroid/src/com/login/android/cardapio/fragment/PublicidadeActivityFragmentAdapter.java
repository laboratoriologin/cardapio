package com.login.android.cardapio.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.login.android.cardapio.R;
import com.login.android.cardapio.model.Publicidade;
import com.login.android.cardapio.util.Constantes;
import com.login.android.cardapio.util.DrawableManager;
import com.login.android.cardapio.util.LoadImage;

public final class PublicidadeActivityFragmentAdapter extends Fragment {

	private static final String KEY_CONTENT = "PublicidadeActivityFragmentAdapter:Content";

	public static PublicidadeActivityFragmentAdapter newInstance(Activity _activity, String content, Publicidade publicidade) {

		PublicidadeActivityFragmentAdapter fragment = new PublicidadeActivityFragmentAdapter();

		fragment.publicidade = publicidade;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			builder.append(content).append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		fragment.mContent = builder.toString();
		fragment.setmActivity(_activity);

		return fragment;
	}

	private Activity mActivity = null;
	private Publicidade publicidade = null;
	private String mContent = "";
	private View view = null;
	private ImageView imageViewMidia = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		this.view = inflater.inflate(R.layout.activity_fragment_publicidade, null);
		this.imageViewMidia = (ImageView) this.view.findViewById(R.id.activity_fragment_publicidade_image_view);

		Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + this.publicidade.getImagem());

		if (img == null) {
			new LoadImage(this.imageViewMidia, this.view.getContext()).execute(Constantes.URL_IMG + this.publicidade.getImagem());
		} else {
			this.imageViewMidia.setImageDrawable(img);
		}

		return this.view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	public Activity getmActivity() {
		return mActivity;
	}

	public void setmActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

}
