package iteamapp.iteamapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.R;

/**
 * Created by Valentin on 2017/4/28.*/



public  class StarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<String> nameDatas;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.star_name);
            image = (ImageView) itemView.findViewById(R.id.star_img);

        }
    }

    public StarAdapter(){
        //this.context = context;
        nameDatas = new ArrayList<String>();
        for (int i=0;i<20;i++){
            nameDatas.add("XX社团"+i);
        }

    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局文件
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_star, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).name.setText(nameDatas.get(position));
            ((ViewHolder) holder).image.setImageResource(R.mipmap.setting_press);

        }
    }

    @Override
    public int getItemCount() {
        return nameDatas.size();
    }

}