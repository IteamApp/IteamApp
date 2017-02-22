package iteamapp.iteamapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RadioGroup;

import iteamapp.iteamapp.adapter.MyFragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private RadioGroup rgGroup;
    private List<Fragment> fragments;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.activity_main);


        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        fragments=new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);


        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        rgGroup.check(R.id.rb_home);
        //当点击底部按钮时切换页面
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_home) {
                    mViewPager.setCurrentItem(0, true);//去掉切换页面的动画
                } else if (i == R.id.rb_news) {
                    mViewPager.setCurrentItem(1, true);
                } else if (i == R.id.rb_service) {
                    mViewPager.setCurrentItem(2, true);
                } else if (i == R.id.rb_gov) {
                    mViewPager.setCurrentItem(3, true);
                }

            }
        });
        //防止频繁的销毁视图
        mViewPager.setOffscreenPageLimit(4);
    }

}
