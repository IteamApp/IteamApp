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



        btn1 = (Button)findViewById(R.id.club_btn1);

        if(userConfig.userID.length()==7){
            btn1.setVisibility(INVISIBLE);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绑定立即报名事件
                Intent i= new Intent(ClubDetail.this,enroll.class);
                startActivity(i);
            }
        });
        initData();

    }


    private void initData(){
        Intent intent=getIntent();
        String id=intent.getStringExtra("team_id");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
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
