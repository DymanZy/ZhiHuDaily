package com.dyman.zhihudaily.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dyman on 2017/2/27.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String TAG = MySQLiteOpenHelper.class.getSimpleName();
    
    private static MySQLiteOpenHelper helper;


    private MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private MySQLiteOpenHelper(Context context, String name) {
        this(context, name, null, 1);
    }


    public static synchronized MySQLiteOpenHelper getInstance(Context context) {
        if (helper == null) {
            helper = new MySQLiteOpenHelper(context, "create_db");
        }

        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //  创建"阅读进度"的数据表
        String sql = "create table if not exists "+TableConfig.TABLE_READ_SCHEDULE+"("
                + TableConfig.ReadSchedule.ID+" integer primary key autoincrement,"
                + TableConfig.ReadSchedule.ARTICLE_ID+" varchar(20),"
                + TableConfig.ReadSchedule.READ_TIME+" varchar(20),"
                + TableConfig.ReadSchedule.READ_RATIO+" varchar(20))";
        Log.i(TAG, "create a database");
        Log.i(TAG, "----------->> sql = "+sql);
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableConfig.TABLE_READ_SCHEDULE);
        onCreate(db);
        Log.i(TAG, "upgrade a database");
    }
}
