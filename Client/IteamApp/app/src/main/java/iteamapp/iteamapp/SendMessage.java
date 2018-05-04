package iteamapp.iteamapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;

/**
 * Created by zqx on 2017/8/2.
 */

public class SendMessage extends Activity {

    private ImageView mBack;
    private TextView tvTitle;
    private Button Send;
    private EditText content;
    private ArrayList<String> arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.little_talk);

        mBack = (ImageView) findViewById(R.id.msg_back);
        tvTitle = (TextView) findViewById(R.id.title_msg);
        Send = (Button) findViewById(R.id.btn_send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        content = (EditText) findViewById(R.id.msg_content);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arraylist = new ArrayList<String>();

        arraylist = getIntent().getStringArrayListExtra("DATA");


    }

    private void submitData() {
        String text = content.getText().toString();

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/getPush.php";
        JSONArray products = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));

        for (int i = 0; i < arraylist.size(); i++)
            params.add(new BasicNameValuePair("people[]", arraylist.get(i)));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        String showContent = "发布成功";
        Toast.makeText(SendMessage.this, showContent, Toast.LENGTH_SHORT).show();
        finish();
    }
}
