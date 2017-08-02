package iteamapp.iteamapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import iteamapp.iteamapp.ChatActivity;
import iteamapp.iteamapp.ChatActivityClub;
import iteamapp.iteamapp.R;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqx on 2017/2/20.
 */

public  class MessageAdapterClub extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public List<String> nameDatas;
    public List<String> infoDatas;
    public  List<String> idDatas;
    public  List<String> logoDatas;

    private static final int HEAD_VIEW = 0;//头布局
    private static final int BODY_VIEW = 2;//内容布局
    private static final int TAG_VIEW = 1;//内容布局



    public MessageAdapterClub(Context context){
        this.context = context;

    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BODY_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);
            MyBodyViewHolder viewHolder = new MyBodyViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof MyBodyViewHolder) {

            ((MessageAdapterClub.MyBodyViewHolder) holder).tv.setText(nameDatas.get(position));
            ((MessageAdapterClub.MyBodyViewHolder) holder).tvinfo.setText(infoDatas.get(position));
            ((MessageAdapterClub.MyBodyViewHolder) holder).image.setImageBitmap(returnBitMap(logoDatas.get(position)));
            ((MessageAdapterClub.MyBodyViewHolder) holder).tvid.setText(idDatas.get(position));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //((MyBodyViewHolder) holder).tv.setBackground(context.getResources().getDrawable(R.drawable.grid_item_bg));
                String pid = idDatas.get(position);
                //TeamConfig.TeamID=pid;
                userConfig.userID=pid;
                Intent in = new Intent(((Activity) context), ChatActivityClub.class);
                //in.putExtra(TAG_PID, pid);
                context.startActivity(in);
                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


    }

    @Override
    public int getItemCount() {
        return nameDatas.size();
    }

    //如果是第一项，则加载头布局
    @Override
    public int getItemViewType(int position) {

        return  BODY_VIEW;
    }

    //头布局的viewholder
    class MyBodyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tv;
        TextView tvinfo;
        TextView tvid;

        public MyBodyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.txt_message_name);
            image = (ImageView) itemView.findViewById(R.id.messge_img);
            tvinfo= (TextView) itemView.findViewById(R.id.msg_recycle_info);
            tvid= (TextView) itemView.findViewById(R.id.tv_msg_id);
        }
    }

    public Bitmap returnBitMap(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}