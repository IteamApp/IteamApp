package iteamapp.iteamapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

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
import iteamapp.iteamapp.adapter.ClubAdapter;
import iteamapp.iteamapp.adapter.MyPageAdapter;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment3 extends Fragment {

    private ProgressDialog pDialog;

    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/team.php";
    JSONArray products = null;
    private  String faculty="";
    private RecyclerView mRecyclerView;
    private View view;
    private boolean start=true;
    private ClubAdapter adapter;
    private RadioGroup allClub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_club);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new ClubAdapter(getContext());
        //initdata();//初始化数据
        new LoadAllArticle().execute();

        allClub= (RadioGroup) view.findViewById(R.id.radio_all);

        allClub.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radio_all_all){
                    faculty="";
                    new LoadAllArticle().execute();

                }
                if (i == R.id.radio_all_hot) {
                    faculty="";
                    new LoadAllArticle().execute();

                }
                if (i == R.id.radio_all_xinxi) {
                    faculty="2";
                    new LoadAllArticle().execute();
                }
                if (i == R.id.radio_all_fazheng) {
                    faculty="3";
                    new LoadAllArticle().execute();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.club_swiperefreshlayout);
       // mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.black);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
    }




    private void initdata(){

        adapter.nameDatas = new ArrayList<String>();
        adapter.logoDatas = new ArrayList<String>();
        adapter.idDatas = new ArrayList<String>();


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(!faculty.equals("")) {
            params.add(new BasicNameValuePair("faculty", faculty));
        }
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());



        try {
            int success=json.getInt("success");
            Log.d("success",success+"");
            if(success!=0) {
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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("正在加载，请稍后....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            initdata();
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            if(start) {

                mRecyclerView.setAdapter(adapter);
                start=false;
            }
            else {
                adapter.notifyDataSetChanged();
            }
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