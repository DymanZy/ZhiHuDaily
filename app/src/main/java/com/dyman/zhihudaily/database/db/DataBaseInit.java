package com.dyman.zhihudaily.database.db;

import android.util.Log;

import com.dyman.zhihudaily.database.dao.ReadSchedule;
import com.dyman.zhihudaily.database.tool.TableOperate;

import java.util.ArrayList;

/**
 * Created by dyman on 2017/2/27.
 */

public class DataBaseInit {

    private static final String TAG = DataBaseInit.class.getSimpleName();

    /**
     *  初始化客户数据表
     */
    public static void initReadScheduleTable() {

        ReadSchedule schedule1 = new ReadSchedule();
        schedule1.setArticleID("100101");
        schedule1.setReadTime(String.valueOf(System.currentTimeMillis()));
        schedule1.setReadRatio("0.45");

        TableOperate operate = new TableOperate();
        operate.insertReadSchedule(schedule1);

//        operate.getReadSchedule(schedule1.getArticleID());

    }

}
