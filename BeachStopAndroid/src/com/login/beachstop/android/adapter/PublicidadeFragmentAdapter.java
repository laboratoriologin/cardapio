package com.login.beachstop.android.adapter;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.login.beachstop.android.fragment.PublicidadeActivityFragmentAdapter;
import com.login.beachstop.android.model.Publicidade;

public class PublicidadeFragmentAdapter extends FragmentPagerAdapter {

	List<Publicidade> listPublicidade = null;
	Activity activity;
	
	public PublicidadeFragmentAdapter(Activity _activity, FragmentManager fm, List<Publicidade> _listImove) {
		super(fm);
		this.listPublicidade = _listImove;
		this.activity = _activity;	 
	}

	@Override
	public Fragment getItem(int position) {
		return PublicidadeActivityFragmentAdapter.newInstance(this.activity, String.valueOf(position), listPublicidade.get(position));
	}

	@Override
	public int getCount() {
		return this.listPublicidade.size();
	}
}
