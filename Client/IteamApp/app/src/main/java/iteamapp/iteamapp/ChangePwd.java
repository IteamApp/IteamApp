package iteamapp.iteamapp;

import android.app.Activity;
import android.os.Bundle;
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

import static iteamapp.iteamapp.Tools.userConfig.userID;

/**
 * Created by zqx on 2018/5/8.
 */

public class ChangePwd extends Activity {
    private ImageView mBack;

    private EditText newPwd;
    private TextView top_name;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pwd);
        top_name = (TextView) findViewById(R.id.top_person);
        newPwd= (EditText) findViewById(R.id.new_pwd);
        top_name.setText("修改密码");
        edit = (Button) findViewById(R.id.btn_change_pwd);
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
    }

    private void editSubmit() {
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/changePwd.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userID));
        params.add(new BasicNameValuePair("pwd", newPwd.getText().toString()));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        String showContent = "修改成功";
        Toast.makeText(ChangePwd.this, showContent, Toast.LENGTH_SHORT).show();
    }

}
