package com.dyman.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.entity.CommentsInfo;
import com.dyman.zhihudaily.entity.ThemeListInfo;
import com.dyman.zhihudaily.utils.common.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dyman on 2017/2/22.
 */

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MyViewHolder> {

    private static final String TAG = ThemeAdapter.class.getSimpleName();
    private Context context;
    private List<ThemeListInfo.OthersBean> datas;

    private AdapterItemClickListener listener;
    public void setAdapterItemClickListener(AdapterItemClickListener listener) {
        this.listener = listener;
    }

    public ThemeAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ThemeListInfo.OthersBean bean = datas.get(position);
        holder.themeName.setText(bean.getName());
        holder.description.setText(bean.getDescription());
        Glide.with(context).load(bean.getThumbnail()).into(holder.themeImage);

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    public ThemeListInfo.OthersBean getItem(int position) {
        return datas.get(position);
    }


    /**
     *  更新 adapter 中的数据
     * @param beanList
     */
    public void updateAdapter(List<ThemeListInfo.OthersBean> beanList) {

        datas.clear();
        for (int i = 0, len = beanList.size(); i < len; i++) {
            datas.add(beanList.get(i));
        }
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView body;
        private TextView themeName;
        private TextView description;
        private ImageView themeImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            body = (CardView) itemView.findViewById(R.id.themeBody_cv_item_theme);
            themeName = (TextView) itemView.findViewById(R.id.themeName_tv_item_theme);
            description = (TextView) itemView.findViewById(R.id.descriptionTheme_tv_item_theme);
            themeImage = (ImageView) itemView.findViewById(R.id.imageTheme_iv_item_theme);

            body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAdapterItemClick(getLayoutPosition());
                    }
                }
            });



        }
    }
}
