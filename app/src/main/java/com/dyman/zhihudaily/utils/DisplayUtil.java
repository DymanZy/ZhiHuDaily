package com.dyman.zhihudaily.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 *  屏幕像素转换工具类
 *
 * Created by dyman on 2017/2/19.
 */

public class DisplayUtil {

    public static int px2dp(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dp2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {

        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {

        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int getScreenWidth(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    public static int getScreenHeight(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    public static float getDisplayDensity(Context context) {

        if (context == null) {
            return -1;
        }
        return context.getResources().getDisplayMetrics().density;
    }


    public static float getStatusBarHeight(Context context) {

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (float) sbar;
    }

}
