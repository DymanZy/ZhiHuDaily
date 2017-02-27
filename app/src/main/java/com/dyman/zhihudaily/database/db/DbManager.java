package com.dyman.zhihudaily.database.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dyman.zhihudaily.ZhiHuDailyApp;

/**
 *  数据库的创建类
 *
 * Created by dyman on 2017/2/27.
 */

public class DbManager {

    private static final String TAG = DbManager.class.getSimpleName();

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
            Log.i(TAG, "DbManager:  获取db ---->> helper.getWritableDatabase()");
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


    /**
     *  视情况而定, 数据库操作频繁的话就在 Activity 的生命周期中管理, 否则建议每次用完就关闭
     */
    public void close() {

        Log.i(TAG, "close: ----- 关闭db和helper, db和manager置空");
        db.close();
        helper.close();
        db = null;
        manager = null;
    }
}
