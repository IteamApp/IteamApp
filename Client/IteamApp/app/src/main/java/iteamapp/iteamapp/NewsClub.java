package iteamapp.iteamapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.androidrichtexteditor.RichTextActivity;
import iteamapp.iteamapp.utils.CustomDiaLog;

import static android.view.View.INVISIBLE;

/**
 * Created by ce on 2017/4/29.
 */

public class NewsClub extends Activity {

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
    private TextView title;
    private String id="";
    private CustomDiaLog dialog;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_news_club);
        mBack = (ImageView) findViewById(R.id.cource_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title= (TextView) findViewById(R.id.free_title);
        title.setText("详情");
        Intent intent=getIntent();
        id=intent.getStringExtra("pid");

        btn1 = (Button)findViewById(R.id.freetime_submit);
        tvcontent = (TextView)findViewById(R.id.new_text1);
        tvUsername = (TextView)findViewById(R.id.new_userName);
        tvtime = (TextView)findViewById(R.id.new_userIntro);
        imgClub= (ImageView) findViewById(R.id.new_userImg);
        imgContent= (ImageView) findViewById(R.id.content_img);
        tvid= (TextView) findViewById(R.id.team_id);

        btn1.setText("操作");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog();
            }
        });

        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showDailog() {

        dialog = new CustomDiaLog(this, R.layout.dialog_evalute,
                R.style.dialog, new CustomDiaLog.LeaveMyDialogListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btn_edit:

                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("role","modify");

                        Intent intent = new Intent(NewsClub.this, SendArticle.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                        dialog.dismiss();
                        break;
                    case R.id.btn_delete:
                        // 打开系统相册
                        deleteData();

                        dialog.dismiss();
                        break;
                    case R.id.btn_cancel:

                        dialog.dismiss();
                        break;

                    default:
                        break;
                }

            }
        });

                // 设置dialog弹出框显示在底部，并且宽度和屏幕一样
        Window window = dialog.getWindow();
        dialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }

    private void deleteData(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String urlcontent = ip.ip+"android/zqx/deleteArticle.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(urlcontent, "GET", params);
        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());
        String showContent = "删除成功！";
        Toast.makeText(NewsClub.this,showContent,Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initData(){


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
                TeamConfig.TeamID=c.getString("team_id");
                tvUsername.setText(c.getString("team_name"));
                tvcontent.setText(c.getString("passage_content"));
                imgClub.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("team_logo")));
                imgContent.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class LoadAllArticle extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsClub.this);
            pDialog.setMessage("正在加载，请稍后....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            initData();
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
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
