package iteamapp.iteamapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.ClubDetail;
import iteamapp.iteamapp.PersonEnroll;
import iteamapp.iteamapp.R;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.personal;

import static android.view.View.VISIBLE;

/**
 * Created by Valentin on 2017/4/28.*/



public  class StarAdapterAction extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String type;

    public List<String> nameDatas;
    public List<String> logoDatas;
    public List<String> idDatas;
    public List<String> isSend;

    public StarAdapterAction(Context context,String type){
        this.context = context;
        this.type=type;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        TextView id;
        CheckBox check;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.star_name);
            image = (ImageView) itemView.findViewById(R.id.star_img);
            check= (CheckBox) itemView.findViewById(R.id.agree_provision_chk);
            id = (TextView) itemView.findViewById(R.id.star_id);


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
            ((ViewHolder) holder).check.setVisibility(VISIBLE);
            ((ViewHolder) holder).check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSend.get(position).equals("0"))
                        isSend.set(position,"1");
                    else
                        isSend.set(position,"0");
                }
            });
            ((ViewHolder) holder).name.setText(nameDatas.get(position));
            ((ViewHolder) holder).id.setText(idDatas.get(position));
            ((ViewHolder) holder).image.setImageBitmap(returnBitMap(logoDatas.get(position)));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if(type.equals("1")||type.equals("2")||type.equals("3")) {
                        String pid = idDatas.get(position);
                        Intent in = new Intent(((Activity) context), ClubDetail.class);
                        TeamConfig.TeamID=pid;
                        context.startActivity(in);
                        ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                    else if (type.equals("4")){
                        String pid = idDatas.get(position);
                        Intent in = new Intent(((Activity) context), personal.class);
                        userConfig.userID=pid;
                        context.startActivity(in);
                        ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                    else {
                        String pid = idDatas.get(position);
                        Intent in = new Intent(((Activity) context), PersonEnroll.class);
                        userConfig.userID=pid;
                        context.startActivity(in);
                        ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }
            });

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

    @Override
    public int getItemCount() {
        return nameDatas.size();
    }

}