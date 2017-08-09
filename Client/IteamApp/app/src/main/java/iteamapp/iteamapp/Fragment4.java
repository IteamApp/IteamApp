package iteamapp.iteamapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import iteamapp.iteamapp.Tools.ToastTool;
import iteamapp.iteamapp.Tools.userConfig;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment4 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout txtFreeTime;
    private LinearLayout topersonal;
    private View view;
    private LinearLayout star;  //关注
    private LinearLayout club;
    private LinearLayout signup;

    private TextView starNum;
    private TextView teamNum;
    private TextView signNum;

    private TextView username;
    private ImageView userimg;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Button btnExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);
        txtFreeTime = (LinearLayout) view.findViewById(R.id.txtFreeTime);
        username= (TextView) view.findViewById(R.id.userName);
        userimg= (ImageView) view.findViewById(R.id.userImg);
        starNum= (TextView) view.findViewById(R.id.starNum);
        signNum=(TextView) view.findViewById(R.id.signNum);
        teamNum= (TextView) view.findViewById(R.id.teamNum);
        btnExit= (Button) view.findViewById(R.id.exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String showContent = "退出成功";
                Toast.makeText(getContext(),showContent,Toast.LENGTH_SHORT).show();
                Intent in = new Intent(((Activity)getActivity()), LoginActivity.class);
                getContext().startActivity(in);
                ((Activity)getActivity()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(((Activity)getActivity()), SetLogo.class);
                in.putExtra("type","1");
                getContext().startActivity(in);
                ((Activity)getActivity()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        txtFreeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),FreeTimeTable.class);
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        //点击关注 打开关注列表
        star = (LinearLayout) view.findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!starNum.getText().toString().equals("0")) {
                    Intent intent = new Intent(getActivity(), StarList.class);
                    intent.putExtra("type", "2");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else{
                    ToastTool.show(getActivity(),"您还没有关注社团，快去关注吧！");
                }

            }
        });

        club = (LinearLayout) view.findViewById(R.id.team);
        club.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!teamNum.getText().toString().equals("0")) {
                    Intent intent = new Intent(getActivity(), StarList.class);
                    intent.putExtra("type", "1");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else{
                    ToastTool.show(getActivity(),"您还没有加入社团，快去报名吧！");
                }
            }
        });

        signup = (LinearLayout) view.findViewById(R.id.sign);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!signNum.getText().toString().equals("0")) {
                    Intent intent = new Intent(getActivity(), StarList.class);
                    intent.putExtra("type", "3");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else{
                    ToastTool.show(getActivity(),"您还没有报名社团，快去报名吧！");
                }
            }
        });

        topersonal=(LinearLayout)view.findViewById(R.id.topersonal);
        topersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),personal_edit.class);
                startActivity(intent);
            }
        });



        return view;

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initData();
//
//        starNum.setText(InitTeam("2"));
//        teamNum.setText(InitTeam("1"));
//        signNum.setText(InitTeam("3"));
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.my_swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {

        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                //initdata();
                starNum.setText(InitTeam("2"));
                teamNum.setText(InitTeam("1"));
                signNum.setText(InitTeam("3"));
            }
        }, 1000);

    }



    private void initData(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getUserInfo.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            username.setText(json.getString("user_name"));
            userimg.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+json.getString("user_head")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private String InitTeam(String type){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getMyTeam.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("type",type));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            int count=json.getInt("count");
            return count+"";

        } catch (JSONException e) {
            e.printStackTrace();
            return "";
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
