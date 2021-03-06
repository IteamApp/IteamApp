package iteamapp.iteamapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;

/**
 * Created by father on 2017/5/6.
 */

public class personal extends Activity {
    private ImageView mBack;

    private TextView username;
    private TextView usercode;
    private TextView sex;
    private TextView major;
    private TextView phone;
    private LinearLayout layout_delete;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        username= (TextView) findViewById(R.id.person_name);
        usercode= (TextView) findViewById(R.id.person_code);
        sex= (TextView) findViewById(R.id.person_sex);
        phone= (TextView) findViewById(R.id.person_phone);
        major= (TextView) findViewById(R.id.person_phone);

        layout_delete= (LinearLayout) findViewById(R.id.layout_delete);
        layout_delete.setVisibility(View.VISIBLE);
        delete= (Button) findViewById(R.id.club_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData("3");
            }
        });
        mBack = (ImageView) findViewById(R.id.person_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InitData();
    }

    private void submitData(String type){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/acceptEnroll.php";
        JSONArray products = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("type", type));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        String showContent = "操作成功";
        Toast.makeText(personal.this, showContent, Toast.LENGTH_SHORT).show();
        finish();
    }


    private void InitData(){

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getUserInfo.php";
        JSONArray products = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            username.setText(json.getString("user_name"));
            sex.setText(json.getString("user_sex"));
            major.setText(json.getString("user_major"));
            phone.setText(json.getString("phone"));
            usercode.setText(json.getString("user_stunum"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
