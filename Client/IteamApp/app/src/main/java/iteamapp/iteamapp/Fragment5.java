package iteamapp.iteamapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.adapter.ManageIndexAdapter;
import iteamapp.iteamapp.Tools.userConfig;

/**
 * Created by zqx on 2017/4/29.
 */

public class Fragment5  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private ManageIndexAdapter adapter;

    private ProgressDialog pDialog;


    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/getTeamArticle.php";
    JSONArray products = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ManageIndexAdapter(getContext());
        new LoadAllArticle().execute();

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

    private void update(){
        for (int i=0;i<10;i++){
            adapter.nameDatas.set(i,"up社团"+i);
        }
        for (int i=0;i<10;i++){
            adapter.infoDatas.add("XX时间"+i+1);
        }

    }

    private  void initImage(){

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getTeamArticle.php";
        JSONArray products = null;

        adapter.imageList = new ArrayList<ImageView>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // products found
            // Getting Array of Products
            products = json.getJSONArray("article");


            for(int i=0;i<products.length();i++){
                JSONObject c = products.getJSONObject(i);
                ImageView image = new ImageView(getActivity());
                image.setBackground(loadImageFromNetwork("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture")));
                adapter.imageList.add(image);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }
        return drawable ;
    }


    private void initdata() {
        adapter.idDatas = new ArrayList<String>();
        adapter.nameDatas = new ArrayList<String>();
        adapter.infoDatas = new ArrayList<String>();
        adapter.logoDatas = new ArrayList<String>();
        adapter.imgDatas = new ArrayList<String>();
        adapter.timeDatas = new ArrayList<String>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // products found
            // Getting Array of Products
            products = json.getJSONArray("article");

            // looping through All Products
            for (int i = 0; i < products.length(); i++) {
                JSONObject c = products.getJSONObject(i);

                // Storing each json item in variable
                adapter.nameDatas.add(c.getString("team_name"));
                adapter.infoDatas.add(c.getString("passage_content"));
                adapter.timeDatas.add(c.getString("passage_time"));
                adapter.imgDatas.add("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture"));
                adapter.idDatas.add(c.getString("id"));
                adapter.logoDatas.add("http://123.206.61.96:8088/android/zqx/"+c.getString("team_logo"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    class LoadAllArticle extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            initdata();
            initImage();
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            mRecyclerView.setAdapter(adapter);
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

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
