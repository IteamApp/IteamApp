package iteamapp.iteamapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import iteamapp.iteamapp.Tools.userConfig;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment6 extends Fragment {

    private LinearLayout txtFreeTime;
    private LinearLayout topersonal;
    private View view;
    private LinearLayout star;  //关注
    private LinearLayout club;
    private LinearLayout signup;

    private TextView teamNum;
    private TextView signNum;

    private TextView username;
    private ImageView userimg;
    private Button btnExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4_club, container, false);
        txtFreeTime = (LinearLayout) view.findViewById(R.id.txtFreeTime_club);
        username= (TextView) view.findViewById(R.id.userName_club);
        userimg= (ImageView) view.findViewById(R.id.userImg_club);
        signNum=(TextView) view.findViewById(R.id.signNum_club);
        teamNum= (TextView) view.findViewById(R.id.teamNum_club);

        btnExit= (Button) view.findViewById(R.id.exit_club);
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

        txtFreeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent in = new Intent(getActivity(),FreeTimeTable.class);
//                getActivity().startActivity(in);
//                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        club = (LinearLayout) view.findViewById(R.id.team_club);
        club.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);
                intent.putExtra("type","4");

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        signup = (LinearLayout) view.findViewById(R.id.sign_club);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);
                intent.putExtra("type","6");

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        topersonal=(LinearLayout)view.findViewById(R.id.topersonal_club);
        topersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent= new Intent(getActivity(),personal.class);
//                startActivity(intent);
            }
        });
        TeamConfig.TeamID="1";
        InitData();

        teamNum.setText(InitMember("4"));
        signNum.setText(InitMember("6"));

        return view;

    }

    private void InitData(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/teamDetail.php";
        JSONArray products = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);
        // Check your log cat for JSON reponse


        try {
            username.setText(json.getString("team_name"));
            userimg.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+json.getString("team_logo")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String InitMember(String type){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getTeamMember.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
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
