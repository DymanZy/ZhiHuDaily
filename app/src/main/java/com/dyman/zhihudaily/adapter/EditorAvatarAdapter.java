package com.dyman.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.entity.StoryBean;
import com.dyman.zhihudaily.entity.ThemeInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dyman on 2017/2/20.
 */

public class EditorAvatarAdapter extends RecyclerView.Adapter<EditorAvatarAdapter.MyViewHolder>{
    private static final String TAG = EditorAvatarAdapter.class.getSimpleName();

    private Context context;
    private List<ThemeInfo.EditorsBean> editorsBeen;

    private AdapterItemClickListener listener;
    public void setAdapterClickListener(AdapterItemClickListener listener) { this.listener = listener; }

    public EditorAvatarAdapter(Context context) {
        this.context = context;
        editorsBeen = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_editor_avatar, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // TODO: 待完善--图片判空处理
        if (editorsBeen.get(position).getAvatar() != null) {
            Glide.with(context)
                .load(editorsBeen.get(position).getAvatar())
                .into(holder.image);

        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return editorsBeen.size();
    }


    public ThemeInfo.EditorsBean getItem(int position) {
        return editorsBeen.get(position);
    }


    /**
     *  更新 adapter 中的信息
     * @param beanList
     */
    public void updateAdapter(List<ThemeInfo.EditorsBean> beanList) {
        if (editorsBeen != null && !beanList.isEmpty()) {
            editorsBeen.clear();
        }
        for (int i = 0, len = beanList.size(); i < len; i++) {
            editorsBeen.add(beanList.get(i));
        }
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;

        public MyViewHolder(View itemView) {

            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.image_item_editor_avatar);

            if (listener != null) {
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onAdapterItemClick(getLayoutPosition());
                    }
                });
            }
        }
    }


}
