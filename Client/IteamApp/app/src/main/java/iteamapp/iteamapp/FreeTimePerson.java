package iteamapp.iteamapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import iteamapp.iteamapp.Tools.RecyclerViewDivider;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.adapter.PersonAdapter;
import iteamapp.iteamapp.adapter.StarAdapter;

/**
 * Created by Valentin on 2017/5/4.
 */

public class FreeTimePerson extends Activity {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private ImageView mBack;
    private TextView tvTitle;
    private ProgressDialog pDialog;
    private TextView nopeople;

    private Boolean no=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_lists);

        mBack = (ImageView) findViewById(R.id.msg_menu_back);
        tvTitle= (TextView) findViewById(R.id.top_msg_title);
        nopeople= (TextView) findViewById(R.id.no_people);
        tvTitle.setText("值班详情");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_star);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=getIntent();
        String time=intent.getStringExtra("time");
        adapter = new PersonAdapter(FreeTimePerson.this,time);


        new LoadAll(time).execute();

        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initdata(String time) {
        adapter.tagDatas = new ArrayList<String>();
        adapter.nameDatas = new ArrayList<String>();
        adapter.logoDatas = new ArrayList<String>();
        adapter.idDatas = new ArrayList<String>();



        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/getTimePeople.php";
        JSONArray products = null;


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("time", time));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // products found
            // Getting Array of Products
            products = json.getJSONArray("people");
            if(products.length()==0){
                no=true;
            }
           else {

                // looping through All Products
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                    // Storing each json item in variable
                    adapter.nameDatas.add(c.getString("username"));
                    adapter.tagDatas.add(c.getString("tag"));
                    adapter.idDatas.add(c.getString("user_stunum"));
                    adapter.logoDatas.add("http://123.206.61.96:8088/android/zqx/" + c.getString("userlogo"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAll extends AsyncTask<String, String, String> {

        String time;

        LoadAll(String time){
            this.time=time;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FreeTimePerson.this);
            pDialog.setMessage("正在加载，请稍后....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            initdata(time);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            recyclerView.setAdapter(adapter);
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(no==true)
                nopeople.setVisibility(View.VISIBLE);
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
