package iteamapp.iteamapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import iteamapp.iteamapp.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private RadioGroup rgGroup;
    private List<Fragment> fragments;
    private ViewPager mViewPager;
    private RadioButton rbhome;
    private RadioButton rbmessage;
    private RadioButton rbclub;
    private RadioButton rbme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.activity_main);


        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);


        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        rgGroup.check(R.id.rb_home);
        rbhome = (RadioButton) findViewById(R.id.rb_home);
        rbmessage = (RadioButton) findViewById(R.id.rb_news);
        rbclub = (RadioButton) findViewById(R.id.rb_service);
        rbme = (RadioButton) findViewById(R.id.rb_gov);
    //获取屏幕宽高
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        Drawable drawable = getResources().getDrawable(R.drawable.btn_tab_home_selector);
        drawable.setBounds(0, 0, height/20, width/8);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbhome.setCompoundDrawables(null, drawable, null, null);//只放上面

        drawable = getResources().getDrawable(R.drawable.btn_tab_news_selector);
        drawable.setBounds(0, 0, height/20, width/8);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbmessage.setCompoundDrawables(null, drawable, null, null);//只放上面

        drawable = getResources().getDrawable(R.drawable.btn_tab_service_selector);
        drawable.setBounds(0, 0, height/20, width/8);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbclub.setCompoundDrawables(null, drawable, null, null);//只放上面

        drawable = getResources().getDrawable(R.drawable.btn_tab_gov_selector);
        drawable.setBounds(0, 0, height/20, width/8);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbme.setCompoundDrawables(null, drawable, null, null);//只放上面

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
