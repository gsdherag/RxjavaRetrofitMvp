package com.shouxiu.rxjavaretrofit.view.home.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shouxiu.rxjavaretrofit.R;
import com.shouxiu.rxjavaretrofit.api.bean.HomeRecommendHotCate;

import java.util.List;


/**
 * @author yeping
 * @date 2018/4/3 9:31
 * TODO
 */

public class HomeOtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    //    全部栏目
    private List<HomeRecommendHotCate> mHomeRecommendHotCate;
    private HomeRecommendAllColumnAdapter mAllColumnAdapter;

    public HomeOtherAdapter(Context context, List<HomeRecommendHotCate> mHomeRecommendHotCate) {
        this.context = context;
        this.mHomeRecommendHotCate = mHomeRecommendHotCate;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColumnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ColumnViewHolder columnViewHolder = (ColumnViewHolder) holder;
        columnViewHolder.tv_column_name.setText(mHomeRecommendHotCate.get(position).getTag_name());
        Glide.with(context).load(mHomeRecommendHotCate.get(position).getIcon_url()).into(columnViewHolder.img_column_icon);
        columnViewHolder.rv_column_list.setLayoutManager(new GridLayoutManager(columnViewHolder.rv_column_list.getContext(), 2, GridLayoutManager.VERTICAL, false));
        mAllColumnAdapter = new HomeRecommendAllColumnAdapter(columnViewHolder.rv_column_list.getContext(), mHomeRecommendHotCate.get(position).getRoom_list());
        columnViewHolder.rv_column_list.setAdapter(mAllColumnAdapter);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mHomeRecommendHotCate.size();
    }

    public class ColumnViewHolder extends RecyclerView.ViewHolder {
        //       栏目 Icon
        public ImageView img_column_icon;
        //        栏目 名称
        public TextView tv_column_name;
        //        加载更多
        public RelativeLayout rl_column_more;
        //        栏目列表
        public RecyclerView rv_column_list;

        public LinearLayout item_home_recommed_girdview;

        public ColumnViewHolder(View itemView) {
            super(itemView);
            img_column_icon = (ImageView) itemView.findViewById(R.id.img_column_icon);
            tv_column_name = (TextView) itemView.findViewById(R.id.tv_column_name);
            rl_column_more = (RelativeLayout) itemView.findViewById(R.id.rl_column_more);
            rv_column_list = (RecyclerView) itemView.findViewById(R.id.rv_column_list);
            item_home_recommed_girdview = (LinearLayout) itemView.findViewById(R.id.item_home_recommed_girdview);
        }
    }

}
