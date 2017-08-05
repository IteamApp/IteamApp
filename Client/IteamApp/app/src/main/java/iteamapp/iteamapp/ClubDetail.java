package iteamapp.iteamapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by ce on 2017/4/27.
 */

public class ClubDetail extends Activity {

    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String urlcontent = ip.ip+"android/zqx/teamDetail.php";


    private ImageView mBack;

    private TextView tvUsername;
    private TextView tvfaculty;
    private ImageView imgClub;
    private TextView tvbrief;
    private LinearLayout member;

    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_club_detial);

        mBack = (ImageView) findViewById(R.id.menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvfaculty = (TextView)findViewById(R.id.club_faculty);
        tvUsername = (TextView)findViewById(R.id.userName);
        tvbrief = (TextView)findViewById(R.id.club_biref);
        imgClub= (ImageView) findViewById(R.id.club_userImg);
        member= (LinearLayout) findViewById(R.id.layout_member);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = getIntent();
//                String id = intent.getStringExtra("team_id");
//                TeamConfig.TeamID = id;
//                Log.d("jkdshjdshhds",id+"");
                Intent intent = new Intent(ClubDetail.this, StarList.class);
                intent.putExtra("type", "4");
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });


        btn1 = (Button)findViewById(R.id.club_btn1);

        try {
            checkTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int flag=check();
        if(flag==0){
            btn1.setBackground(ClubDetail.this.getResources().getDrawable(R.drawable.bg_18));
            btn1.setText("已报名");
        }
        else if(flag==2){
            btn1.setBackground(ClubDetail.this.getResources().getDrawable(R.drawable.bg_18));
            btn1.setText("已加入该社团");
        }
        else {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //绑定立即报名事件
                    Intent i= new Intent(ClubDetail.this,enroll.class);
                    startActivity(i);
                }
            });
        }

        if(userConfig.userID.length()==7){
            btn1.setVisibility(INVISIBLE);
        }

        initData();

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

    @Override
    protected void onResume() {
        super.onResume();


        try {
            checkTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int flag=check();
        if(flag==0){
            btn1.setBackground(ClubDetail.this.getResources().getDrawable(R.drawable.bg_18));
            btn1.setText("已报名");
        }
        else if(flag==2){
            btn1.setBackground(ClubDetail.this.getResources().getDrawable(R.drawable.bg_18));
            btn1.setText("已加入该社团");
        }
        else {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //绑定立即报名事件
                    Intent i= new Intent(ClubDetail.this,enroll.class);
                    startActivity(i);
                }
            });
        }
    }

    private int check(){
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
            return success;

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }

    }


    private void initData(){
        Intent intent=getIntent();
        String id=intent.getStringExtra("team_id");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(urlcontent, "GET", params);
        // Check your log cat for JSON reponse


        try {
            tvUsername.setText(json.getString("team_name"));
            tvfaculty.setText(json.getString("team_faculty"));
            tvbrief.setText(json.getString("team_brief"));
            imgClub.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+json.getString("team_logo")));

        } catch (JSONException e) {
            e.printStackTrace();
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
