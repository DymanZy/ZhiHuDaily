package com.dyman.zhihudaily.utils;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.dyman.zhihudaily.R;

/**
 * Created by dyman on 2017/2/23.
 */

public class DialogUtils {

    /**
     *  显示评论操作的对话框
     * @param context
     * @param listener
     */
    public static void showCommentDialog(Context context, View.OnClickListener listener) {

        View v = View.inflate(context, R.layout.dialog_comment, null);
        v.findViewById(R.id.agree_dialog_comment).setOnClickListener(listener);
        v.findViewById(R.id.report_dialog_comment).setOnClickListener(listener);
        v.findViewById(R.id.copy_dialog_comment).setOnClickListener(listener);
        v.findViewById(R.id.reply_dialog_comment).setOnClickListener(listener);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v)
                .setCancelable(true)
                .create();
        builder.show();
    }


    public static void showShareDialog(Context context, View.OnClickListener listener) {

        View v = View.inflate(context, R.layout.dialog_share, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v)
                .setCancelable(true)
                .create();
        builder.show();

    }

}
