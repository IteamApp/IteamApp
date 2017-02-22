package iteamapp.iteamapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;




import java.util.ArrayList;

import iteamapp.iteamapp.adapter.MyPageAdapter;


/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View view;

    private MyPageAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new MyPageAdapter(getContext());

        initdata();//初始化数据
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.demo_swiperefreshlayout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);


    }


    private void initdata() {



        //初始化recycleview的数据
        adapter.nameDatas = new ArrayList<String>();
        adapter.infoDatas = new ArrayList<String>();
        adapter.idDatas = new ArrayList<String>();

        for (int i=0;i<10;i++){
            adapter.nameDatas.add("XX社团"+i+1);
        }

        for (int i=0;i<10;i++){
            adapter.infoDatas.add("XX时间"+i+1);
        }

        for (int i=0;i<10;i++){
            adapter.idDatas.add(i+1+"");
        }





        int[] imageResIDs = {R.drawable.a, R.drawable.b, R.drawable.c};
        adapter.imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageResIDs.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setBackgroundResource(imageResIDs[i]);
            adapter.imageList.add(image);
        }
    }

    @Override
    public void onRefresh() {

        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                initdata();
                adapter.notifyDataSetChanged();

            }
        }, 1000);

    }

}
