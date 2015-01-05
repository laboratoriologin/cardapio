package com.login.beachstop.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.TabHost;

import com.login.beachstop.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.android.utils.Constantes;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class CardapioApp extends Application {

    private TabHost mTabHost;
    private DataManager dataManager;
    private String keyCardapio;

    @Override
    public void onCreate() {
        super.onCreate();

        setDataManager(new DataManager(this));

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(25)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public TabHost getTabHost() {
        return mTabHost;
    }

    public void setTabHost(TabHost mTabHost) {
        this.mTabHost = mTabHost;
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
