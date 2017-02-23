package com.dyman.zhihudaily.utils.common;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *  SnackBar工具类
 *
 * Created by dyman on 2017/2/19.
 */

public class SnackbarUtil {

    public static void showMessage(View view, String text) {

        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

}
