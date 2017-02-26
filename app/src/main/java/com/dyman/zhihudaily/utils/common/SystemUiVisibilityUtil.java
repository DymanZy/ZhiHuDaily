package com.dyman.zhihudaily.utils.common;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 *  StatusBar隐藏显示工具类
 *
 * Created by dyman on 2017/2/26.
 */

public class SystemUiVisibilityUtil {

    public static void addFlags(View view, int flags) {

        view.setSystemUiVisibility(view.getSystemUiVisibility() | flags);
    }


    public static void clearFlags(View view, int flags) {

        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~flags);
    }


    public static boolean hasFlags(View view, int flags) {
        return (view.getSystemUiVisibility() & flags) == flags;
    }


    /**
     *  显示或隐藏 StatusBar
     *
     * @param window
     * @param enable    false 显示, true 隐藏
     */
    public static void hideStatusBar(Window window, boolean enable) {

        WindowManager.LayoutParams p = window.getAttributes();
        if (enable) {
            p.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            p.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        window.setAttributes(p);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

}
