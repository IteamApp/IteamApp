package iteamapp.iteamapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import iteamapp.iteamapp.adapter.ClubAdapter;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment3 extends Fragment {

    private RecyclerView mRecyclerView;

    private View view;

    private ClubAdapter adapter;

    private RadioGroup allClub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_club);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new ClubAdapter(getContext());
        //initdata();//初始化数据
        mRecyclerView.setAdapter(adapter);
        allClub= (RadioGroup) view.findViewById(R.id.radio_all);

        allClub.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_all_hot) {
                    hotdata();
                    adapter.notifyDataSetChanged();

                }
                if (i == R.id.radio_all_xinxi) {
                    xinxidata();
                    adapter.notifyDataSetChanged();

                }
                if (i == R.id.radio_all_fazheng) {
                    fazhengdata();
                    adapter.notifyDataSetChanged();

                }

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.club_swiperefreshlayout);
       // mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.black);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void hotdata() {
        for (int i=0;i<10;i++){
            adapter.nameDatas.set(i,"热门社团"+i);
        }

    }

    private void xinxidata() {
        for (int i=0;i<10;i++){
            adapter.nameDatas.set(i,"信息社团"+i);
        }

    }

    private void fazhengdata() {
        for (int i=0;i<10;i++){
            adapter.nameDatas.set(i,"法政社团"+i);
        }

    }
}