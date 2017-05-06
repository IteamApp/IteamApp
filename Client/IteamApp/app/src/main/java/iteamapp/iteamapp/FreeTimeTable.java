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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iteamapp.iteamapp.FreeTimeTableUI.AbsGridAdapter;
import iteamapp.iteamapp.FreeTimeTableUI.MyAdapter;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.userConfig;

public class FreeTimeTable extends Activity {

    private ProgressDialog pDialog;
    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/getFreeTime.php";
    JSONArray products = null;
    private Spinner spinner;
    private GridView detailCource;
    private MyAdapter adapter;
    private String[][] contents;
    private AbsGridAdapter secondAdapter;
    private List<String> dataList;
    private ArrayAdapter<String> spinnerAdapter;
    private ImageView mBack;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cource);
        mBack = (ImageView) findViewById(R.id.cource_menu_back);
        submit= (Button) findViewById(R.id.freetime_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
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
        secondAdapter = new AbsGridAdapter(this);
        new LoadFreeTime().execute();
        //////////////创建Spinner数据
//        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(spinnerAdapter);
    }



    private void submitData(){

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/updateFreetime.php";
        JSONArray products = null;

        String info="";

        for(int i=0;i<6;i++) {
            for (int j = 0; j < 7; j++) {
                if(contents[i][j].contains("有课")){
                    info+=(j+1)+"."+(i+1)+";";
                }
            }
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("info", info));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        Log.d("All Products: ", json.toString());
        String showContent = "提交成功";
        Toast.makeText(FreeTimeTable.this,showContent,Toast.LENGTH_SHORT).show();

    }


    class LoadFreeTime extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FreeTimeTable.this);
            pDialog.setMessage("Loading products. Please wait...");
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
        params.add(new BasicNameValuePair("id", userConfig.userID));
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
                String team_name=c.getString("team_name");
                String tag=c.getString("tag");
                if(team_name.equals("")){
                    team_name="有课";
                }
                int x=Integer.parseInt(time.substring(0,1));
                int y=Integer.parseInt(time.substring(2,3));
                contents[y-1][x-1]=team_name+tag;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}