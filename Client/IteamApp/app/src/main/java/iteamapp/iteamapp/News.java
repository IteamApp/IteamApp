package iteamapp.iteamapp;

import android.app.Activity;
<<<<<<< HEAD
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
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.userConfig;

import static android.view.View.INVISIBLE;

=======
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

>>>>>>> origin/master
/**
 * Created by ce on 2017/4/29.
 */

public class News extends Activity {
<<<<<<< HEAD

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

=======
    private Button btn1;
    private TextView text1;
    private ArrayAdapter<String> arr_adapter;
>>>>>>> origin/master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news);
<<<<<<< HEAD
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

        imgClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = tvid.getText().toString();
                Intent in = new Intent(News.this, ClubDetail.class);
                in.putExtra("team_id", pid);
                startActivity(in);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

=======

        btn1 = (Button)findViewById(R.id.btn1);
        text1 = (TextView)findViewById(R.id.text1);
        String[]arr_data = {"andriod1"};
        arr_adapter = new ArrayAdapter<String>(this,R.layout.item_news,arr_data);
>>>>>>> origin/master

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
<<<<<<< HEAD
        initData();
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
                tvUsername.setText(c.getString("team_name"));
                tvcontent.setText(c.getString("passage_content"));
                Log.d("ds","fsdf");
                imgClub.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("team_logo")));
                imgContent.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture")));
            }

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
=======

>>>>>>> origin/master
    }
}
