package iteamapp.iteamapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class enroll extends Activity {

    private ImageView mBack;

    private EditText username;
    private EditText usercode;
    private EditText sex;
    private EditText major;
    private EditText introduction;
    private EditText phone;
    private Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll);
        username= (EditText) findViewById(R.id.enroll_name);
        usercode= (EditText) findViewById(R.id.enroll_stunumber);
        sex= (EditText) findViewById(R.id.enroll_sex);
        phone= (EditText) findViewById(R.id.enroll_phonenumber);
        introduction= (EditText) findViewById(R.id.enroll_intorduction);
        major= (EditText) findViewById(R.id.enroll_major);
        submit= (Button) findViewById(R.id.enroll_btn1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitData();
            }
        });


        mBack = (ImageView) findViewById(R.id.enroll_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        InitData();
    }


    private void SubmitData(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/enroll.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("team", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("brief", introduction.getText().toString()));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        String showContent = "报名成功";
        Toast.makeText(enroll.this,showContent,Toast.LENGTH_SHORT).show();

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
