package com.dyman.zhihudaily.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dyman.zhihudaily.R;

/**
 * Created by dyman on 2017/3/2.
 */

public class DateViewHolder extends RecyclerView.ViewHolder {

    private final TextView dateTv;

    public DateViewHolder(View itemView) {
        super(itemView);

        dateTv = (TextView) itemView.findViewById(R.id.textView_item_recycler_date);
    }


    public void bindDate(String date) {

        dateTv.setText(date);
    }
}
