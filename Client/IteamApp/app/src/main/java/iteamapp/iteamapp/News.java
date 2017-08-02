package iteamapp.iteamapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;

import static android.view.View.INVISIBLE;

/**
 * Created by ce on 2017/4/29.
 */

public class News extends Activity {

    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String urlcontent = ip.ip+"android/zqx/articleDetail.php";
    JSONArray products = null;

    private ImageView mBack;
    private Button btn1;
    private TextView tvcontent;

    private TextView tvUsername;
    private TextView tvtime;
    private ImageView imgClub;
    private ImageView imgContent;
    private TextView tvid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news);
        mBack = (ImageView) findViewById(R.id.new_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn1 = (Button)findViewById(R.id.news_btn1);
        tvcontent = (TextView)findViewById(R.id.new_text1);
        tvUsername = (TextView)findViewById(R.id.new_userName);
        tvtime = (TextView)findViewById(R.id.new_userIntro);
        imgClub= (ImageView) findViewById(R.id.new_userImg);
        imgContent= (ImageView) findViewById(R.id.content_img);
        tvid= (TextView) findViewById(R.id.team_id);

        if(userConfig.userID.length()==7){
            btn1.setVisibility(INVISIBLE);
        }


        try {
            checkTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(check()==false){
            btn1.setBackground(News.this.getResources().getDrawable(R.drawable.bg_18));
            btn1.setText("已报名");
        }
        else {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //绑定立即报名事件
                    Intent i= new Intent(News.this,enroll.class);
                    startActivity(i);
                }
            });
        }


        imgClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = tvid.getText().toString();
                Intent in = new Intent(News.this, ClubDetail.class);
                in.putExtra("team_id", pid);
                TeamConfig.TeamID=pid;
                startActivity(in);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            checkTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(check()==false){
            btn1.setBackground(News.this.getResources().getDrawable(R.drawable.bg_18));
            btn1.setText("已报名");
        }
        else {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //绑定立即报名事件
                    Intent i= new Intent(News.this,enroll.class);
                    startActivity(i);
                }
            });
        }
    }

    private void initData(){
        Intent intent=getIntent();
        String id=intent.getStringExtra("pid");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(urlcontent, "GET", params);
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
                tvtime.setText(c.getString("passage_time"));
                tvid.setText(c.getString("team_id"));
                TeamConfig.TeamID=c.getString("team_id");
                tvUsername.setText(c.getString("team_name"));
                tvcontent.setText(c.getString("passage_content"));
                imgClub.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("team_logo")));
                imgContent.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean check(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/enroll.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("team", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("brief", "1"));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);
        try {
            // products found
            // Getting Array of Products
            int success=json.getInt("success");
            return success==1?true:false;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    private Boolean checkTime() throws JSONException {
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/getTime.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("start", ""));
        params.add(new BasicNameValuePair("end", ""));
        params.add(new BasicNameValuePair("type", "2"));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);

        Log.d("da",dateNowStr);

        String satrtStr=json.getString("start");
        String endtStr=json.getString("end");

//        Log.d("res1",compare_date(endtStr,dateNowStr)+"");
//        Log.d("res2",compare_date(dateNowStr,satrtStr)+"");

        if(compare_date(endtStr,dateNowStr)==1 && compare_date(dateNowStr,satrtStr)==1) {
            btn1.setVisibility(View.VISIBLE);
            return true;
        }
        else {
            btn1.setVisibility(INVISIBLE);
            return false;
        }

    }

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
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
