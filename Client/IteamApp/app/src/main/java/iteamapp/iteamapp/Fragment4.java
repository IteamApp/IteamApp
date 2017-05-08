package iteamapp.iteamapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import iteamapp.iteamapp.Tools.userConfig;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment4 extends Fragment {

    private Button txtFreeTime;
    private LinearLayout topersonal;
    private View view;
    private TextView star;  //关注
    private TextView club;
    private TextView signup;

    private TextView starNum;
    private TextView teamNum;
    private TextView signNum;

    private TextView username;
    private ImageView userimg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);
        txtFreeTime = (Button) view.findViewById(R.id.txtFreeTime);
        username= (TextView) view.findViewById(R.id.userName);
        userimg= (ImageView) view.findViewById(R.id.userImg);
        starNum= (TextView) view.findViewById(R.id.starNum);
        signNum=(TextView) view.findViewById(R.id.signNum);
        teamNum= (TextView) view.findViewById(R.id.teamNum);

        txtFreeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(getActivity(),FreeTimeTable.class);
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        //点击关注 打开关注列表
        star = (TextView) view.findViewById(R.id.starNum);
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);
                intent.putExtra("type","2");

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        club = (TextView) view.findViewById(R.id.teamNum);
        club.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);
                intent.putExtra("type","1");

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        signup = (TextView) view.findViewById(R.id.signNum);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);
                intent.putExtra("type","3");

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        topersonal=(LinearLayout)view.findViewById(R.id.topersonal);
        topersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),personal.class);
                startActivity(intent);
            }
        });

        InitData();

        starNum.setText(InitTeam("2"));
        teamNum.setText(InitTeam("1"));
        signNum.setText(InitTeam("3"));

        return view;

    }

    private void InitData(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getUserInfo.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
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
