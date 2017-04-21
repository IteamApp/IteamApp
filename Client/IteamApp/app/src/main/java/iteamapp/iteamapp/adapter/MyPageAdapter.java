package iteamapp.iteamapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import iteamapp.iteamapp.ItemDetail;
import iteamapp.iteamapp.R;
import com.viewpagerindicator.CirclePageIndicator;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zqx on 2017/2/20.
 */

public  class MyPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG_PID = "pid";

    private Context context;
    public List<String> nameDatas;
    public List<String> infoDatas;
    public ArrayList<ImageView> imageList;
    public  List<String> idDatas;
    private static final int HEAD_VIEW = 0;//头布局
    private static final int BODY_VIEW = 2;//内容布局
    private static final int TAG_VIEW = 1;//内容布局
    private CustomPopWindow mCustomPopWindow;
    private RadioButton mButton3;
    private RadioGroup rgGroup;
    private int Flag=0;

    private MyAdapter mPagerAdapter = new MyAdapter();
    public MyPageAdapter(Context context){
        this.context = context;
    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_VIEW) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.headview_recycleview, parent, false);
            MyHeadViewHolder viewHolder = new MyHeadViewHolder(view);
            return viewHolder;
        }

        if (viewType == TAG_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.girdview, parent, false);
            MyTabViewHolder viewHolder = new MyTabViewHolder(view);
            return viewHolder;
        }

        if (viewType == BODY_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycle, parent, false);
            MyBodyViewHolder viewHolder = new MyBodyViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof MyHeadViewHolder) {
            ((MyHeadViewHolder) holder).mViewPager.setAdapter(mPagerAdapter);
            ((MyHeadViewHolder) holder).indicator.onPageSelected(0);
            ((MyHeadViewHolder) holder).indicator.setViewPager(((MyHeadViewHolder) holder).mViewPager);
            ((MyHeadViewHolder) holder).indicator.setSnap(true);
        }
        if (holder instanceof MyTabViewHolder) {
            rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(Flag==0) {
                        if (i == R.id.club_mine) {
                            mydata();
                            notifyDataSetChanged();
                        }
                        if (i == R.id.club_hot) {
                            hotdata();
                            notifyDataSetChanged();
                        }
                        if (i == R.id.club_shaixuan) {
                            Log.d("dcs", "shfk");
                            View contentView = LayoutInflater.from(context).inflate(R.layout.pop_menu, null);
                            //处理popWindow 显示内容
                            handleLogic(contentView);
                            //创建并显示popWindow
                            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                                    .setView(contentView)
                                    .create()
                                    .showAsDropDown(mButton3, 0, 20);

                        }
                    }
                    else {
                        Flag=0;
                    }
                }
            });
        }
        if (holder instanceof MyBodyViewHolder) {
            ((MyBodyViewHolder) holder).tv.setText(nameDatas.get(position-2));
            ((MyBodyViewHolder) holder).tvinfo.setText(infoDatas.get(position-2));
            ((MyBodyViewHolder) holder).tvid.setText(idDatas.get(position-2));
            ((MyBodyViewHolder) holder).image.setImageResource(R.mipmap.setting_press);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (position > 1) {
                    String pid = idDatas.get(position - 2);
                    Intent in = new Intent(((Activity)context), ItemDetail.class);
                    in.putExtra(TAG_PID, pid);
                    context.startActivity(in);
                    ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }


        });
    }


    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "点击 Item菜单1";
                        mydata1();
                        notifyDataSetChanged();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;

                    case R.id.menu2:
                        showContent = "点击 Item菜单2";
                        mydata2();
                        notifyDataSetChanged();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;

                    case R.id.menu3:
                        showContent = "点击 Item菜单3";
                        mydata3();
                        notifyDataSetChanged();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;

                    case R.id.menu4:
                        showContent = "点击 Item菜单4";
                        mydata4();
                        notifyDataSetChanged();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;

                    case R.id.menu5:
                        showContent = "点击 Item菜单5" ;
                        mydata5();
                        notifyDataSetChanged();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;
                }
                Toast.makeText(context,showContent,Toast.LENGTH_SHORT).show();

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
    }

    private void mydata() {

        for (int i=0;i<10;i++) {
            nameDatas.set(i, "我的社团" + i + 1);
        }
    }

    private void mydata1() {

        for (int i=0;i<10;i++) {
            nameDatas.set(i, "Item_one我的社团" + i + 1);
        }
    }

    private void mydata2() {

        for (int i=0;i<10;i++) {
            nameDatas.set(i, "Item_two我的社团" + i + 1);
        }
    }

    private void mydata3() {

        for (int i=0;i<10;i++) {
            nameDatas.set(i, "Item_Three我的社团" + i + 1);
        }
    }

    private void mydata4() {

        for (int i=0;i<10;i++) {
            nameDatas.set(i, "Item_four我的社团" + i + 1);
        }
    }

    private void mydata5() {

        for (int i=0;i<10;i++) {
            nameDatas.set(i, "Item_five我的社团" + i + 1);
        }
    }

    private void hotdata() {
        for (int i=0;i<10;i++) {
            nameDatas.set(i, "热门社团" + i + 1);
        }
    }

    @Override
    public int getItemCount() {
        return nameDatas.size() + 2;
    }

    //如果是第一项，则加载头布局
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        } else if(position==1) {
            return TAG_VIEW;
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

    class MyTabViewHolder extends RecyclerView.ViewHolder {

        public MyTabViewHolder(View itemView) {
            super(itemView);
            rgGroup = (RadioGroup) itemView.findViewById(R.id.club_group);
            mButton3 = (RadioButton) itemView.findViewById(R.id.club_shaixuan);
        }
    }
    class MyBodyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tvinfo;
        TextView tvid;
        ImageView image;

        public MyBodyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.recycle_tv);
            tvinfo = (TextView) itemView.findViewById(R.id.recycle_info);
            tvid = (TextView) itemView.findViewById(R.id.recycle_pid);
            image = (ImageView) itemView.findViewById(R.id.recycle_img);

        }
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