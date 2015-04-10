package com.login.beachstop.android.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Publicidade;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.DrawableManager;
import com.login.beachstop.android.utils.LoadImage;

/**
 * Created by Argus on 29/10/2014.
 */
public class PublicidadeFragment extends Fragment {

    private static final String KEY_CONTENT = "PublicidadeFragment:Content";
    private Activity mActivity = null;
    private Publicidade publicidade = null;
    private String mContent = "";
    private View view = null;
    private ImageView imageViewMidia = null;

    public static PublicidadeFragment newInstance(Activity activity, String content, Publicidade publicidade) {

        PublicidadeFragment fragment = new PublicidadeFragment();
        fragment.publicidade = publicidade;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 20; i++) {

            builder.append(content).append(" ");

        }

        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();
        fragment.setmActivity(activity);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {

            mContent = savedInstanceState.getString(KEY_CONTENT);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_publicidade, null);

        this.imageViewMidia = (ImageView) this.view.findViewById(R.id.fragment_publicidade_image_view);

        if (!TextUtils.isEmpty(this.publicidade.getImagem())) {

            Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + this.publicidade.getImagem());

            if (img == null) {

                new LoadImage(this.imageViewMidia, this.view.getContext()).execute(Constantes.URL_IMG + this.publicidade.getImagem());

            } else {

                this.imageViewMidia.setImageDrawable(img);

            }

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
