package com.login.beachstop.android.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.login.beachstop.android.DefaultActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.model.Publicidade;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.util.DrawableManager;
import com.login.beachstop.android.util.LoadImage;

public final class PublicidadeActivityFragment extends Fragment {

	private static final String KEY_CONTENT = "PublicidadeActivityFragmentAdapter:Content";

	public static PublicidadeActivityFragment newInstance(Activity _activity, String content, Publicidade publicidade) {

		PublicidadeActivityFragment fragment = new PublicidadeActivityFragment();

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

		if (this.publicidade.getImagem().length() != 0) {

			Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + this.publicidade.getImagem());

			if (img == null) {
				new LoadImage(this.imageViewMidia, this.view.getContext()).execute(Constantes.URL_IMG + this.publicidade.getImagem());
			} else {
				this.imageViewMidia.setImageDrawable(img);
			}

			this.imageViewMidia.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					PublicidadeDetalheFragment newFragment = new PublicidadeDetalheFragment();
					Bundle args = new Bundle();
					args.putSerializable(Constantes.ARG_PUBLICIDADE, publicidade);
					newFragment.setArguments(args);
					FragmentTransaction transaction = ((DefaultActivity) getmActivity()).getSupportFragmentManager().beginTransaction();

					transaction.replace(R.id.activity_home_fragment_layout, newFragment);
					transaction.addToBackStack(null);

					// Commit the transaction
					transaction.commit();
				}
			});
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
