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

import iteamapp.iteamapp.ClubDetail;
import iteamapp.iteamapp.News;
import iteamapp.iteamapp.R;


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

public  class ClubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public List<String> nameDatas;
    public List<String> logoDatas;
    public List<String> idDatas;

    private static final int HEAD_VIEW = 0;//头布局
    private static final int BODY_VIEW = 2;//内容布局
    private static final int TAG_VIEW = 1;//内容布局



    public ClubAdapter(Context context){
        this.context = context;
        nameDatas = new ArrayList<String>();
        for (int i=0;i<20;i++){
            nameDatas.add("XX社团"+i+1);
        }

    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BODY_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_club, parent, false);
            MyBodyViewHolder viewHolder = new MyBodyViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof MyBodyViewHolder) {
            ((MyBodyViewHolder) holder).tv.setText(nameDatas.get(position));
            ((MyBodyViewHolder) holder).image.setImageBitmap(returnBitMap(logoDatas.get(position)));
            ((MyBodyViewHolder) holder).tvid.setText(idDatas.get(position));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String pid = idDatas.get(position);
                Intent in = new Intent(((Activity)context), ClubDetail.class);
                in.putExtra("team_id", pid);
                context.startActivity(in);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
 /*       if (position == 0) {
            return HEAD_VIEW;
        } else if(position==1) {
            return TAG_VIEW;
        }
        else {return BODY_VIEW;}*/
        return  BODY_VIEW;
    }

    //头布局的viewholder
    class MyBodyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView image;
        TextView tvid;

        public MyBodyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.txt_club_name);
            tvid = (TextView) itemView.findViewById(R.id.all_club_id);
            image = (ImageView) itemView.findViewById(R.id.recycle_club_img);

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