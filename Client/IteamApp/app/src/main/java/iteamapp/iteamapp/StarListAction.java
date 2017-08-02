package iteamapp.iteamapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.RecyclerViewDivider;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.adapter.StarAdapter;
import iteamapp.iteamapp.adapter.StarAdapterAction;
import iteamapp.iteamapp.androidrichtexteditor.RichTextActivity;
import iteamapp.iteamapp.utils.CustomDiaLog;

/**
 * Created by Valentin on 2017/5/4.
 */

public class StarListAction extends Activity {

    private RecyclerView recyclerView;
    private StarAdapterAction adapter;
    private ImageView mBack;
    private TextView tvTitle;
    private ProgressDialog pDialog;
    private Button action;
    private CustomDiaLog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_lists);

        mBack = (ImageView) findViewById(R.id.msg_menu_back);
        tvTitle= (TextView) findViewById(R.id.top_msg_title);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        action= (Button) findViewById(R.id.msg_action);
        action.setVisibility(View.VISIBLE);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_star);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        adapter = new StarAdapterAction(StarListAction.this,type);
        if(type.equals("1")){
            tvTitle.setText("我的社团");
        }
        if(type.equals("2")){
            tvTitle.setText("关注社团");
        }
        if(type.equals("3")){
            tvTitle.setText("报名社团");
        }
        if(type.equals("4")){
            tvTitle.setText("社团成员");
        }
        if(type.equals("6")){
            tvTitle.setText("报名人员");
        }

    }

    public void showDailog() {

        dialog = new CustomDiaLog(this, R.layout.dialog_club,
                R.style.dialog, new CustomDiaLog.LeaveMyDialogListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btn_edit:

                        ArrayList<String> tagData=new ArrayList<String>();

                        for(int i=0;i<adapter.isSend.size();i++){
                            if(adapter.isSend.get(i).equals("1"))
                                tagData.add(adapter.idDatas.get(i));
                        }

                        Intent intent = new Intent(StarListAction.this, SendMessage.class);
                        intent.putStringArrayListExtra("DATA", tagData);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        dialog.dismiss();
                        break;
                    case R.id.btn_delete:


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

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        new LoadAll(userConfig.userID,type).execute();

        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initdata(String usercode, String type) {
        adapter.idDatas = new ArrayList<String>();
        adapter.nameDatas = new ArrayList<String>();
        adapter.logoDatas = new ArrayList<String>();
        adapter.isSend = new ArrayList<String>();

        if(type.equals("1")||type.equals("2")||type.equals("3")) {

            IpConfig ip = new IpConfig();
            JSONParser jParser = new JSONParser();
            String url = ip.ip + "android/zqx/getMyTeam.php";
            JSONArray products = null;


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", usercode));
            params.add(new BasicNameValuePair("type", type));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // products found
                // Getting Array of Products
                products = json.getJSONArray("team");

                // looping through All Products
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                    // Storing each json item in variable
                    adapter.nameDatas.add(c.getString("team_name"));

                    adapter.idDatas.add(c.getString("team_id"));
                    adapter.logoDatas.add("http://123.206.61.96:8088/android/zqx/" + c.getString("team_logo"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{

            IpConfig ip = new IpConfig();
            JSONParser jParser = new JSONParser();
            String url = ip.ip + "android/zqx/getTeamMember.php";
            JSONArray products = null;


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Log.d("team",TeamConfig.TeamID);
            params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
            params.add(new BasicNameValuePair("type", type));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // products found
                // Getting Array of Products
                products = json.getJSONArray("member");

                // looping through All Products
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                    // Storing each json item in variable
                    adapter.isSend.add("0");
                    adapter.nameDatas.add(c.getString("user_name"));
                    adapter.idDatas.add(c.getString("user_stunum"));
                    adapter.logoDatas.add("http://123.206.61.96:8088/android/zqx/" + c.getString("user_head"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAll extends AsyncTask<String, String, String> {

        String usercode;
        String type;

        LoadAll(String usercode,String type){
            this.usercode=usercode;
            this.type=type;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StarListAction.this);
            pDialog.setMessage("正在加载，请稍后....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            initdata(usercode,type);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            recyclerView.setAdapter(adapter);
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

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
