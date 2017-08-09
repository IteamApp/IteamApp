package iteamapp.iteamapp;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.adapter.ManageIndexAdapter;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.androidrichtexteditor.RichTextActivity;
/**
 * Created by zqx on 2017/4/29.
 */

public class Fragment5  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private ManageIndexAdapter adapter;
    private Button btnAdd;
    private ProgressDialog pDialog;
    private Boolean isFirst=true;


    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/getTeamArticle.php";
    JSONArray products = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_club, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_club);
        btnAdd= (Button) view.findViewById(R.id.club_add);
        btnAdd.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ManageIndexAdapter(getContext());
        new LoadAllArticle().execute();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("role","add");
                Intent intent = new Intent(getActivity(), SendArticle.class);

                intent.putExtras(bundle);
                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(!isFirst){
//            new LoadAllArticle().execute();
//        }
//        isFirst=false;
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.demo_swiperefreshlayout_club);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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


            for(int i=0;i<3;){
                JSONObject c = products.getJSONObject(i);
                ImageView image = new ImageView(getActivity());
                if(!c.getString("passage_picture").equals("")) {
                    image.setBackground(loadImageFromNetwork("http://123.206.61.96:8088/android/zqx/" + c.getString("passage_picture")));
                    adapter.imageList.add(image);
                    i++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            InputStream is=new URL(imageUrl).openStream();
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(is,"image.jpg");
            is.close();

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
            pDialog.setMessage("正在加载，请稍后....");
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
            adapter.notifyDataSetChanged();
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
