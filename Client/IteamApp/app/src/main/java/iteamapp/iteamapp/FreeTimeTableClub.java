package iteamapp.iteamapp;

/**
 * Created by zqx on 2017/4/29.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.FreeTimeTableUI.AbsGridAdapter;
import iteamapp.iteamapp.FreeTimeTableUI.AbsGridAdapterClub;
import iteamapp.iteamapp.FreeTimeTableUI.MyAdapter;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;

public class FreeTimeTableClub extends Activity {

    private ProgressDialog pDialog;
    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/freeTimeClub.php";
    JSONArray products = null;
    private Spinner spinner;
    private GridView detailCource;
    private TextView title;
    private MyAdapter adapter;
    private String[][] contents;
    private AbsGridAdapterClub secondAdapter;
    private List<String> dataList;
    private ArrayAdapter<String> spinnerAdapter;
    private ImageView mBack;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freetime_club);
        mBack = (ImageView) findViewById(R.id.common_menu_back);
        title= (TextView) findViewById(R.id.common_title);
        title.setText("社团值班表");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        spinner = (Spinner)findViewById(R.id.switchWeek);
        detailCource = (GridView)findViewById(R.id.courceDetail);
        ///////////////第一种方式创建Adapater
//        List<String> list = init();
//        adapter = new MyAdapter(this, list);
//        detailCource.setAdapter(adapter);
        ///////////////第二种方式创建Adapter

        //////////////创建Spinner数据
//        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(spinnerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String showContent = "成功取消值班";
//        Toast.makeText(FreeTimeTableClub.this, showContent, Toast.LENGTH_SHORT).show();
        secondAdapter = new AbsGridAdapterClub(this);
        new LoadFreeTime().execute();
    }

    class LoadFreeTime extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FreeTimeTableClub.this);
            pDialog.setMessage("正在加载，请稍后....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            fillStringArray();
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            secondAdapter.setContent(contents, 6, 7);
            detailCource.setAdapter(secondAdapter);
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }





    public void fillStringArray() {
        contents = new String[6][7];
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                contents[i][j]="";
            }
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // products found
            // Getting Array of Products
            products = json.getJSONArray("freetime");

            // looping through All Products
            for (int i = 0; i < products.length(); i++) {
                JSONObject c = products.getJSONObject(i);
                String time=c.getString("time");
                String name=c.getString("username");
                int x=Integer.parseInt(time.substring(0,1));
                int y=Integer.parseInt(time.substring(2,3));
                contents[y-1][x-1]+=name+"\n";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}