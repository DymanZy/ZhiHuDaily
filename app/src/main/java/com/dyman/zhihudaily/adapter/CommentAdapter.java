package com.dyman.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.entity.CommentsInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dyman on 2017/2/22.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private Context context;
    private List<CommentsInfo> datas;

    public CommentAdapter(Context context) {
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //TODO: put data to widget
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout bodyLl;
        private CircleImageView circleImageView;
        private TextView userNameTv;
        private TextView markNumsTv;
        private TextView commentInfoTv;
        private TextView timeTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            bodyLl = (LinearLayout) itemView.findViewById(R.id.body_ll_item_comment);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.userImage_civ_item_comment);
            userNameTv = (TextView) itemView.findViewById(R.id.userName_tv_item_comment);
            markNumsTv = (TextView) itemView.findViewById(R.id.markNum_tv_item_comment);
            commentInfoTv = (TextView) itemView.findViewById(R.id.commentInfo_tv_item_comment);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv_item_comment);
        }
    }
}
