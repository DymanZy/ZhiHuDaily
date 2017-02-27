package com.dyman.zhihudaily;

import android.app.Application;

import com.dyman.zhihudaily.database.db.DataBaseInit;
import com.facebook.stetho.Stetho;

/**
 * Created by dyman on 2017/2/19.
 */

public class ZhiHuDailyApp extends Application{

    public static ZhiHuDailyApp mInstance;
    public static ZhiHuDailyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        init();
    }


    private void init() {

//        DataBaseInit.initReadScheduleTable();

        //初始化Stetho调试工具
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

}
