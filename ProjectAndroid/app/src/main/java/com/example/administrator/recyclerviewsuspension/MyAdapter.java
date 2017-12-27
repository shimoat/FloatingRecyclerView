package com.example.administrator.recyclerviewsuspension;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.recyclerviewsuspension.bean.ComingBean;

import java.util.List;

/**
 * Created by JiaPing on 2017/12/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<ComingBean> datas;

    public MyAdapter(Context context, List<ComingBean> datas) {
        Log.d("MyAdapter", "789:" + 789);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MyAdapter", "456:" + 456);
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imagUrl = datas.get(position).getImg();
        String newImagUrl = imagUrl.replaceAll("w.h", "50.80");

        Log.d("MyAdapter", "123:" + 123);

        Glide.with(context)
                .load(newImagUrl)
                .error(R.mipmap.ic_launcher)
                .into(holder.img);

        holder.tv_time.setText(datas.get(position).getPubDesc());
        holder.tv_title.setText(datas.get(position).getNm());
        holder.tv_introduce.setText(datas.get(position).getDesc());
        holder.tv_score_major_real.setText(datas.get(position).getDur()+"");
        holder.tv_score_people_real.setText(datas.get(position).getPn()+"");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_title;
        TextView tv_introduce;
        TextView tv_score_major_real;
        TextView tv_score_people_real;
        TextView tv_time;

        public ViewHolder(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_introduce = (TextView) view.findViewById(R.id.tv_introduce);
            tv_score_major_real = (TextView) view.findViewById(R.id.tv_score_major_real);
            tv_score_people_real = (TextView) view.findViewById(R.id.tv_score_people_real);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
