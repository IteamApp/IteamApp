package iteamapp.iteamapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;


/**
 * Created by zqx on 2017/4/30.
 */

public class LoginActivity extends Activity {
    private EditText tvUsername;
    private EditText tvPwd;
    private Button btnLogin;

    private ProgressDialog pDialog;

    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/login.php";
    JSONArray products = null;
    Handler handler;

    private Boolean isOk=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvUsername = (EditText) findViewById(R.id.et_mobile);
        tvPwd = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tvUsername.getText().toString();
                String password = tvPwd.getText().toString();

                if(username.equals("")||password.equals("")){
                    String showContent = "请输入用户名和密码";
                    Toast.makeText(LoginActivity.this,showContent,Toast.LENGTH_SHORT).show();
                }
                else {
                    new Login().execute();
                }
            }
        });

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("正在登陆，请稍后.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            String username = tvUsername.getText().toString();
            String password = tvPwd.getText().toString();


            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("pwd", password));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);



            try {
                // products found
                // Getting Array of Products

                int success = json.getInt("success");

                if (success == 1) {
                    String user = json.getString("username");
                    Log.d("user", user);
                    if (user.length() == 11) {
                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                        in.putExtra("username", user);
                        userConfig.userID = user;
                        Set<String> set = new HashSet<>();
                        set.add(user);//名字任意，可多添加几个,能区别就好了
                        JPushInterface.setTags(LoginActivity.this, set, null);//设置标签
                        startActivity(in);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }

                    if (user.length() == 7) {
                        Intent in = new Intent(LoginActivity.this, MainActivity_Club.class);
                        in.putExtra("username", user);
                        userConfig.userID = user;
                        String team_id = json.getString("team_id");
                        TeamConfig.TeamID = team_id;
                        startActivity(in);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                } else {
                    isOk=true;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            if(isOk){
                String showContent = "用户名或密码错误！";
                Toast.makeText(LoginActivity.this,showContent,Toast.LENGTH_SHORT).show();
            }
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
        }
    }

//    public void showToast() {
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), "登录失败！",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
