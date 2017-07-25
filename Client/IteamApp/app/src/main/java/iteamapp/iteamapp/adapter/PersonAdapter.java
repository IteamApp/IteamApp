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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.ClubDetail;
import iteamapp.iteamapp.FreeTimeTable;
import iteamapp.iteamapp.R;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Valentin on 2017/4/28.*/



public  class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private  String time;

    public List<String> nameDatas;
    public List<String> logoDatas;
    public List<String> tagDatas;
    public List<String> idDatas;

    public PersonAdapter(Context context, String time){
        this.context = context;
        this.time=time;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        TextView tag;
        TextView id;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.star_name);
            image = (ImageView) itemView.findViewById(R.id.star_img);
            tag = (TextView) itemView.findViewById(R.id.star_tag);
            id = (TextView) itemView.findViewById(R.id.star_id);
            layout= (LinearLayout) itemView.findViewById(R.id.star_layout);

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).name.setText(nameDatas.get(position));
            ((ViewHolder) holder).id.setText(idDatas.get(position));
            String tag=tagDatas.get(position);
//            ((ViewHolder) holder).tag.setText(tag);
            if (tag.equals("2")){
                ((ViewHolder) holder).tag.setVisibility(VISIBLE);
                ((ViewHolder) holder).layout.setBackground(context.getResources().getDrawable(R.drawable.grid_item_bg));
            }
            else {
                ((ViewHolder) holder).layout.setBackground(context.getResources().getDrawable(R.drawable.bg_none));
            }

            ((ViewHolder) holder).image.setImageBitmap(returnBitMap(logoDatas.get(position)));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String tag = tagDatas.get(position);
                    String id = idDatas.get(position);
                    IpConfig ip = new IpConfig();
                    JSONParser jParser = new JSONParser();
                    String url = ip.ip+"android/zqx/updatePeopleTime.php";
                    JSONArray products = null;


                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id", id));
                    params.add(new BasicNameValuePair("tag", tag));
                    params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
                    params.add(new BasicNameValuePair("time", time));
                    // getting JSON string from URL
                    JSONObject json = jParser.makeHttpRequest(url, "GET", params);

                    Log.d("All Products: ", json.toString());
                    if(tag.equals("2")) {
                        String showContent = "成功取消值班";
                        Toast.makeText(context, showContent, Toast.LENGTH_SHORT).show();
                        ((ViewHolder) holder).tag.setVisibility(INVISIBLE);
                        tagDatas.set(position,"1");
                        ((ViewHolder) holder).layout.setBackground(context.getResources().getDrawable(R.drawable.bg_none));
                    }
                    else{
                        String showContent = "成功安排值班";
                        Toast.makeText(context, showContent, Toast.LENGTH_SHORT).show();
                        ((ViewHolder) holder).tag.setVisibility(VISIBLE);
                        tagDatas.set(position,"2");
                        ((ViewHolder) holder).layout.setBackground(context.getResources().getDrawable(R.drawable.grid_item_bg));
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