package com.dyman.zhihudaily.database.db;

import android.database.sqlite.SQLiteDatabase;

import com.dyman.zhihudaily.ZhiHuDailyApp;

/**
 *  数据库的创建类
 *
 * Created by dyman on 2017/2/27.
 */

public class DbManager {

    private static DbManager manager;
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;


    /**
     *  私有化构造器
     */
    private DbManager() {

        helper = MySQLiteOpenHelper.getInstance(ZhiHuDailyApp.getInstance());
        if (db == null) {
            db = helper.getWritableDatabase();
        }

//        helper.onUpgrade(db, 1, 2);
    }


    /**
     *  DbManager的单例
     * @return
     */
    public static DbManager newInstance() {

        if (manager == null) {
            manager = new DbManager();
        }
        return manager;
    }


    /**
     *  获取数据库的对象
     * @return
     */
    public SQLiteDatabase getDataBase() {
        return db;
    }
}
