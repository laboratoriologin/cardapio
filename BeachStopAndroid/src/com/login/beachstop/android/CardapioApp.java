package com.login.beachstop.android;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TabHost;

import com.login.beachstop.android.model.CategoriaCardapioItem;
import com.login.beachstop.android.sqlite.dao.DataManager;
import com.login.beachstop.android.util.GCM;

public class CardapioApp extends Application implements LocationListener {

	private Double latitude;
	private Double longitude;
	private LocationManager locationManager;
	private DataManager dataManager;
	private List<CategoriaCardapioItem> listaItemCardapio;
	private TabHost mTabHost;
	private Long qtdMesa;

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
		
		GCM.ativa(getApplicationContext());
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
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

	public List<CategoriaCardapioItem> getListaItemCardapio() {
		return listaItemCardapio;
	}

	public void setListaItemCardapio(List<CategoriaCardapioItem> listaItemCardapio) {
		this.listaItemCardapio = listaItemCardapio;
	}

	public TabHost getTabHost() {
		return mTabHost;
	}

	public void setTabHost(TabHost mTabHost) {
		this.mTabHost = mTabHost;
	}

	/**
	 * @return the qtdMesa
	 */
	public Long getQtdMesa() {
		return qtdMesa;
	}

	/**
	 * @param qtdMesa
	 *            the qtdMesa to set
	 */
	public void setQtdMesa(Long qtdMesa) {
		this.qtdMesa = qtdMesa;
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

}
