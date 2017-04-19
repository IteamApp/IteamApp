package iteamapp.iteamapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import gg.bean.CourseBean;
import gg.view.ScheduleView;

/**
 * Created by zqx on 2017/4/19.
 */

public class FreeTimeTable extends AppCompatActivity {

    private ScheduleView mScheduleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freetime_table);
        init();
    }

    private void init() {
        mScheduleView= (ScheduleView) findViewById(R.id.sv);

        List<CourseBean> mList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mList.add(new CourseBean());
        }
        mScheduleView.fillData(new int[]{2016,11,29},5,mList);
    }
}