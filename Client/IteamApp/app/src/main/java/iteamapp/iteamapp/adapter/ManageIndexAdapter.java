package iteamapp.iteamapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.News;
import iteamapp.iteamapp.R;

/**
 * Created by zqx on 2017/4/29.
 */

public class ManageIndexAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG_PID = "pid";

    private Context context;
    public List<String> nameDatas;
    public List<String> infoDatas;
    public ArrayList<ImageView> imageList;
    public  List<String> idDatas;
    public  List<String> imgDatas;
    public  List<String> logoDatas;
    public  List<String> timeDatas;
    private static final int HEAD_VIEW = 0;//头布局
    private static final int BODY_VIEW = 2;//内容布局
    private static final int TAG_VIEW = 1;//内容布局


    private ManageIndexAdapter.MyAdapter mPagerAdapter = new ManageIndexAdapter.MyAdapter();
    public ManageIndexAdapter(Context context){
        this.context = context;

    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_VIEW) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.headview_recycleview, parent, false);
            ManageIndexAdapter.MyHeadViewHolder viewHolder = new ManageIndexAdapter.MyHeadViewHolder(view);
            return viewHolder;
        }


        if (viewType == BODY_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycle, parent, false);
            ManageIndexAdapter.MyBodyViewHolder viewHolder = new ManageIndexAdapter.MyBodyViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof ManageIndexAdapter.MyHeadViewHolder) {
            ((ManageIndexAdapter.MyHeadViewHolder) holder).mViewPager.setAdapter(mPagerAdapter);
            ((ManageIndexAdapter.MyHeadViewHolder) holder).indicator.onPageSelected(0);
            ((ManageIndexAdapter.MyHeadViewHolder) holder).indicator.setViewPager(((ManageIndexAdapter.MyHeadViewHolder) holder).mViewPager);
            ((ManageIndexAdapter.MyHeadViewHolder) holder).indicator.setSnap(true);
        }

        if (holder instanceof ManageIndexAdapter.MyBodyViewHolder) {
            ((ManageIndexAdapter.MyBodyViewHolder) holder).tv.setText(nameDatas.get(position-1));
            ((ManageIndexAdapter.MyBodyViewHolder) holder).tvinfo.setText(timeDatas.get(position-1));
            ((ManageIndexAdapter.MyBodyViewHolder) holder).tvmore.setText(infoDatas.get(position-1));
            ((ManageIndexAdapter.MyBodyViewHolder) holder).img_article.setImageBitmap(returnBitMap(imgDatas.get(position-1)));
            ((ManageIndexAdapter.MyBodyViewHolder) holder).imglogo.setImageBitmap(returnBitMap(logoDatas.get(position-1)));
            ((ManageIndexAdapter.MyBodyViewHolder) holder).tvid.setText(idDatas.get(position-1));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (position > 0) {
                    String pid = idDatas.get(position - 1);
                    Intent in = new Intent(((Activity)context), News.class);
                    in.putExtra(TAG_PID, pid);
                    context.startActivity(in);
                    ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return nameDatas.size() + 1;
    }

    //如果是第一项，则加载头布局
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        }
        else {return BODY_VIEW;}
    }

    //头布局的viewholder
    class MyHeadViewHolder extends RecyclerView.ViewHolder {
        ViewPager mViewPager;
        CirclePageIndicator indicator;

        public MyHeadViewHolder(View itemView) {
            super(itemView);
            mViewPager = (ViewPager) itemView.findViewById(R.id.vp_tab_headview);
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
        }
    }


    class MyBodyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tvinfo;
        TextView tvid;
        ImageView img_article;
        TextView tvmore;
        ImageView imglogo;

        public MyBodyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.recycle_tv);
            tvinfo = (TextView) itemView.findViewById(R.id.recycle_info);
            tvid = (TextView) itemView.findViewById(R.id.recycle_pid);
            img_article = (ImageView) itemView.findViewById(R.id.img_article);
            tvmore= (TextView) itemView.findViewById(R.id.recycle_more);
            imglogo= (ImageView) itemView.findViewById(R.id.recycle_img);

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

    //viewpager的adapter
    class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
            return imageList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Integer.toString(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
