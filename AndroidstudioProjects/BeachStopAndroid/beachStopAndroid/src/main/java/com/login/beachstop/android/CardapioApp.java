package com.login.beachstop.android;

import android.app.Application;
import android.content.Context;
import android.widget.TabHost;

import com.login.beachstop.android.model.CategoriaCardapioItem;
import com.login.beachstop.android.sqlite.dao.DataManager;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class CardapioApp extends Application {

    private DataManager dataManager;
    private List<CategoriaCardapioItem> listaItemCardapio;
    private TabHost mTabHost;
    private Long qtdMesa;

    @Override
    public void onCreate() {
        super.onCreate();
        setDataManager(new DataManager(this));
        configuraDefaultImageLoder(getApplicationContext());

    }

    public static void configuraDefaultImageLoder(Context context) {

        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSizePercentage(30)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheSize(60 * 1024 * 1024)
                .diskCacheFileCount(150)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
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
     * @param qtdMesa the qtdMesa to set
     */
    public void setQtdMesa(Long qtdMesa) {
        this.qtdMesa = qtdMesa;
    }
}
