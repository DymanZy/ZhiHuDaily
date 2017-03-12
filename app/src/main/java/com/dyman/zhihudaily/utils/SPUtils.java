package com.dyman.zhihudaily.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.dyman.zhihudaily.ZhiHuDailyApp;

/**
 * Created by dyman on 2017/3/1.
 */

public class SPUtils {

    private static final String SP_NAME = "zhi_hu_daily";

    private static final String IS_FIRST_OPEN = "is_first_open";

    private static final String IS_AUTO_LOAD_IMAGE = "is_auto_load_image";

    private static SharedPreferences sp;


    /**
     *  是否第一次打开APP（显示引导界面）
     * @return
     */
    public static boolean isFirstOpen() {

        sp = ZhiHuDailyApp.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(IS_FIRST_OPEN, true);
    }


    /**
     *  设置非第一次打开APP
     */
    public static void alreadyOpen() {

        sp = ZhiHuDailyApp.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_FIRST_OPEN, false);
        editor.commit();
    }


    /**
     *  移动网络环境下是否自动加载图片
     * @return
     */
    public static boolean isAutoLoadImage() {

        sp = ZhiHuDailyApp.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(IS_AUTO_LOAD_IMAGE, true);
    }


    /**
     *  设置移动网络环境下是否自动加载图片
     * @param isAuto
     */
    public static void setAutoLoadImage(boolean isAuto) {

        sp = ZhiHuDailyApp.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_AUTO_LOAD_IMAGE, isAuto);
        editor.commit();
    }

}
