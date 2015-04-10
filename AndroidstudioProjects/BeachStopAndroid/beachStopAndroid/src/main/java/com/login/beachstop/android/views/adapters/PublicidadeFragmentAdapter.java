package com.login.beachstop.android.views.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.login.beachstop.android.fragments.PublicidadeFragment;
import com.login.beachstop.android.models.Publicidade;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class PublicidadeFragmentAdapter extends FragmentPagerAdapter {

    List<Publicidade> publicidades = null;
    Activity activity;

    public PublicidadeFragmentAdapter(Activity activity, FragmentManager fragmentManager, List<Publicidade> publicidades) {

        super(fragmentManager);
        this.publicidades = publicidades;
        this.activity = activity;

    }

    @Override
    public Fragment getItem(int position) {
        return PublicidadeFragment.newInstance(this.activity, String.valueOf(position), publicidades.get(position));
    }

    @Override
    public int getCount() {
        return this.publicidades.size();
    }
}
