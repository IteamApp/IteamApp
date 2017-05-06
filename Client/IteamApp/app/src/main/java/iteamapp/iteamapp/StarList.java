package iteamapp.iteamapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import iteamapp.iteamapp.Tools.RecyclerViewDivider;
import iteamapp.iteamapp.adapter.StarAdapter;

/**
 * Created by Valentin on 2017/5/4.
 */

public class StarList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StarAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_lists);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_star);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StarAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
    }
}
