package com.dyman.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.entity.CommentsInfo;
import com.dyman.zhihudaily.utils.common.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dyman on 2017/2/22.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private static final String TAG = CommentAdapter.class.getSimpleName();
    private Context context;
    private List<CommentsInfo.CommentsBean> datas;

    private AdapterItemClickListener listener;
    public void setAdapterItemClickListener(AdapterItemClickListener listener) {
        this.listener = listener;
    }

    public CommentAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //TODO: put data to widget
        CommentsInfo.CommentsBean commentsBean = datas.get(position);

        holder.userNameTv.setText(commentsBean.getAuthor());
        holder.markNumsTv.setText("点赞 " + String.valueOf(commentsBean.getLikes()));
        holder.contentTv.setText(commentsBean.getContent());
        holder.timeTv.setText(TimeUtil.parseDateTime(commentsBean.getTime()));
        Glide.with(context).load(commentsBean.getAvatar()).into(holder.circleImageView);

        if (commentsBean.getReply_to() != null && commentsBean.getReply_to().getStatus() == 0) {

            holder.replyTv.setVisibility(View.VISIBLE);
            holder.replyTv.setText("//" + commentsBean.getReply_to().getAuthor() + ": "
                    + commentsBean.getReply_to().getContent());

            // 在 TextView 完成测量和定位即将绘制时再获取其行数,否则会返回0
            holder.replyTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // 获取完行数注销监听
                    holder.replyTv.getViewTreeObserver().removeOnPreDrawListener(this);

                    //  回复内容超过两行显示"展开"按钮
                    if (holder.replyTv.getLineCount() > 2) {
                        holder.spreadReplyBtn.setVisibility(View.VISIBLE);
                        holder.replyTv.setMaxLines(2);
                    }

                    return false;
                }
            });



        }

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    public CommentsInfo.CommentsBean getItem(int position) {
        return datas.get(position);
    }


    /**
     *  更新 adapter 中的数据
     * @param beanList
     */
    public void updateAdapter(List<CommentsInfo.CommentsBean> beanList) {

        datas.clear();
        for (int i = 0, len = beanList.size(); i < len; i++) {
            datas.add(beanList.get(i));
        }
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout bodyLl;
        private CircleImageView circleImageView;
        private TextView userNameTv;
        private TextView markNumsTv;
        private TextView contentTv;
        private TextView replyTv;
        private TextView timeTv;
        private Button spreadReplyBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            bodyLl = (LinearLayout) itemView.findViewById(R.id.body_ll_item_comment);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.userImage_civ_item_comment);
            userNameTv = (TextView) itemView.findViewById(R.id.userName_tv_item_comment);
            markNumsTv = (TextView) itemView.findViewById(R.id.markNum_tv_item_comment);
            contentTv = (TextView) itemView.findViewById(R.id.content_tv_item_comment);
            replyTv = (TextView) itemView.findViewById(R.id.replyContent_tv_item_comment);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv_item_comment);
            spreadReplyBtn = (Button) itemView.findViewById(R.id.spreadContent_btn_item_comment);
            spreadReplyBtn.setSelected(true);

            bodyLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAdapterItemClick(getLayoutPosition());
                    }
                }
            });

            spreadReplyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spreadReplyBtn.isSelected()) {
                        spreadReplyBtn.setSelected(false);
                        spreadReplyBtn.setText("收起");
                        replyTv.setMaxLines(10);
                    } else {
                        spreadReplyBtn.setSelected(true);
                        spreadReplyBtn.setText("展开");
                        replyTv.setMaxLines(2);
                    }
                }
            });

        }
    }
}
