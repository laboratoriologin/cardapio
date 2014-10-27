package com.login.beachstop.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TabHost;

import com.login.beachstop.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.android.utils.Constantes;

public class CardapioApp extends Application implements LocationListener {

    private Double latitude;
    private Double longitude;
    private LocationManager locationManager;
    private TabHost mTabHost;
    private DataManager dataManager;
    private String keyCardapio;

    @Override
    public void onCreate() {
        super.onCreate();

        setDataManager(new DataManager(this));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
            latitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
            longitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public TabHost getTabHost() {
        return mTabHost;
    }

    public void setTabHost(TabHost mTabHost) {
        this.mTabHost = mTabHost;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public String getKeyCardapio() {

        if (this.keyCardapio == null) {

            this.keyCardapio = getSharedPreferences(Constantes.SHARED_PREFS, 0).getString(Constantes.KEY_CARDAPIO, null);

        }

        return this.keyCardapio;

    }

    public void setKeyCardapio(String keyCardapio) {

        this.keyCardapio = keyCardapio;

        SharedPreferences.Editor editor = getSharedPreferences(Constantes.SHARED_PREFS, 0).edit();
        editor.putString(Constantes.KEY_CARDAPIO, this.keyCardapio);
    }
}
