package com.shouxiu.rxjavaretrofit.view.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shouxiu.rxjavaretrofit.R;
import com.shouxiu.rxjavaretrofit.api.bean.HomeRecommendHotCate;
import com.shouxiu.rxjavaretrofit.utils.CalculationUtils;
import com.shouxiu.rxjavaretrofit.view.home.activity.PcLiveVideoActivity;
import com.shouxiu.rxjavaretrofit.view.home.activity.PhoneLiveVideoActivity;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author yeping
 * @date 2018/4/10 14:21
 * TODO
 */

public class HomeRecommendAllColumnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeRecommendHotCate.RoomListEntity> mRommListEntity;
    private Context context;

    public HomeRecommendAllColumnAdapter(Context context, List<HomeRecommendHotCate.RoomListEntity> mRommListEntity) {
        this.context = context;
        this.mRommListEntity = mRommListEntity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recommend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HotColumnHolder) {
            bindHotColumun((HotColumnHolder) holder, position);
        }
    }

    private void bindHotColumun(HotColumnHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCornersTransformation(20, 0))
                .placeholder(R.drawable.image_loading_5_3)
                .error(R.drawable.load_err);
        Glide.with(context)
                .load(Uri.parse(mRommListEntity.get(position).getVertical_src()))
                .apply(requestOptions)
                .into(holder.img_item_gridview);
        holder.img_item_gridview.setImageURI(Uri.parse(mRommListEntity.get(position).getVertical_src()));
        holder.tv_column_item_nickname.setText(mRommListEntity.get(position).getRoom_name());
        holder.tv_nickname.setText(mRommListEntity.get(position).getNickname());
        holder.tv_online_num.setText(CalculationUtils.getOnLine(mRommListEntity.get(position).getOnline()));
        if (mRommListEntity.get(position).getCate_id().equals("201")) {
            holder.rl_live_icon.setBackgroundResource(R.drawable.search_header_live_type_mobile);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                颜值栏目 竖屏播放
                if (mRommListEntity.get(position).getCate_id().equals("201")) {
                    Intent intent = new Intent(context, PhoneLiveVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Room_id", mRommListEntity.get(position).getRoom_id());
                    bundle.putString("Img_Path", mRommListEntity.get(position).getVertical_src());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PcLiveVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Room_id", mRommListEntity.get(position).getRoom_id());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRommListEntity.size();
    }

    public class HotColumnHolder extends RecyclerView.ViewHolder {
        //        图片
        public ImageView img_item_gridview;
        //        房间名称
        public TextView tv_column_item_nickname;
        //        在线人数
        public TextView tv_online_num;
        //        昵称
        public TextView tv_nickname;
        //        Icon
        public RelativeLayout rl_live_icon;

        public HotColumnHolder(View view) {
            super(view);
            img_item_gridview = (ImageView) view.findViewById(R.id.img_item_gridview);
            tv_column_item_nickname = (TextView) view.findViewById(R.id.tv_column_item_nickname);
            tv_online_num = (TextView) view.findViewById(R.id.tv_online_num);
            tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
            rl_live_icon = (RelativeLayout) view.findViewById(R.id.rl_live_icon);
        }
    }
}
