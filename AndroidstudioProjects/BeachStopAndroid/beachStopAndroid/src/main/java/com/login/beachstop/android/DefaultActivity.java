package com.login.beachstop.android;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TabHost;

import com.login.beachstop.android.managers.sqlite.dao.DataManager;

public class DefaultActivity extends FragmentActivity {

    public void backPressed(View view) {
        super.onBackPressed();
    }

    public Double getLatitudeAtual() {
        CardapioApp app = (CardapioApp) getApplication();
        return app.getLatitude();
    }

    public Double getLongitudeAtual() {
        CardapioApp app = (CardapioApp) getApplication();
        return app.getLongitude();
    }

    public TabHost getTabHost() {
        return ((CardapioApp) getApplication()).getTabHost();
    }

    public void setTabHost(TabHost mTabHost) {
        ((CardapioApp) getApplication()).setTabHost(mTabHost);
    }

    public DataManager getDataManager() {
        return ((CardapioApp) getApplication()).getDataManager();
    }
}
