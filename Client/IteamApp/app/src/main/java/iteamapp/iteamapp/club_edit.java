package iteamapp.iteamapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

public class club_edit extends Activity {
    private ImageView mBack;

    private EditText username;
    private TextView usercode;
    private EditText major;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_info);
        username= (EditText) findViewById(R.id.person_name);
        usercode= (TextView) findViewById(R.id.person_code);
        major= (EditText) findViewById(R.id.person_major);


        edit= (Button) findViewById(R.id.person_edit);
        edit.setVisibility(View.VISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSubmit();
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

    private void editSubmit(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/updateTeamInfo.php";
        JSONArray products = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("name", username.getText().toString()));
        params.add(new BasicNameValuePair("brief", major.getText().toString()));

        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());
        String showContent = "修改成功";
        Toast.makeText(club_edit.this, showContent, Toast.LENGTH_SHORT).show();



    }


    private void InitData(){

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String urlcontent = ip.ip+"android/zqx/teamDetail.php";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(urlcontent, "GET", params);
        // Check your log cat for JSON reponse


        try {
            username.setText(json.getString("team_name"));
            usercode.setText(json.getString("team_faculty"));
            major.setText(json.getString("team_brief"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
