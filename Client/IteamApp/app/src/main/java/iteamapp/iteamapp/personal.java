package iteamapp.iteamapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        username= (TextView) findViewById(R.id.person_name);
        usercode= (TextView) findViewById(R.id.person_code);
        sex= (TextView) findViewById(R.id.person_sex);
        phone= (TextView) findViewById(R.id.person_phone);
        major= (TextView) findViewById(R.id.person_phone);

        mBack = (ImageView) findViewById(R.id.person_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InitData();
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
