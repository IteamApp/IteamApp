package iteamapp.iteamapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.adapter.MyPageAdapter;


/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog pDialog;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private MyPageAdapter adapter;

    private String userID;

    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/getArticle.php";
    JSONArray products = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        //Intent intent=getActivity().getIntent();
       // userID=intent.getStringExtra("username");
        userID="15020031000";
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new MyPageAdapter(getContext(),userID);

        new LoadAllArticle(userID,"1").execute();
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


    private  void initImage(String usercode,String type){

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/Article.php";
        JSONArray products = null;

        adapter.imageList = new ArrayList<ImageView>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", usercode));
        params.add(new BasicNameValuePair("type", type));
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




    private void initdata(String usercode,String type) {
        adapter.idDatas = new ArrayList<String>();
        adapter.nameDatas = new ArrayList<String>();
        adapter.infoDatas = new ArrayList<String>();
        adapter.logoDatas = new ArrayList<String>();
        adapter.imgDatas = new ArrayList<String>();
        adapter.timeDatas = new ArrayList<String>();



        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", usercode));
        params.add(new BasicNameValuePair("type", type));
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

    @Override
    public void onRefresh() {

        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                //initdata();
                adapter.notifyDataSetChanged();

            }
        }, 1000);

    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllArticle extends AsyncTask<String, String, String> {

        String usercode;
        String type;

        LoadAllArticle(String usercode,String type){
            this.usercode=usercode;
            this.type=type;
        }

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
            initdata(usercode,type);
            initImage(usercode,type);
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
}
