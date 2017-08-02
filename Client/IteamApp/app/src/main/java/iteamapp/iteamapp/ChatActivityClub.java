package iteamapp.iteamapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import iteamapp.iteamapp.R;
import iteamapp.iteamapp.TestData;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.adapter.ChatAdapter;
import iteamapp.iteamapp.model.ChatModel;
import iteamapp.iteamapp.model.ItemModel;

public class ChatActivityClub extends Activity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private String content;
    private ImageView mBack;

    private LinearLayout layout;
    private EditText msg;
    private Button send;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        title= (TextView) findViewById(R.id.top_msg_title);
        title.setText("消息详情");
        layout= (LinearLayout) findViewById(R.id.layout_tuisong);
        layout.setVisibility(View.VISIBLE);
        msg= (EditText) findViewById(R.id.et_tuisong);
        send= (Button) findViewById(R.id.club_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });
        mBack = (ImageView) findViewById(R.id.msg_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatAdapter());
        adapter.replaceAll(TestDataClub.getTestAdData());
    }

    private void sendMsg(){

        String text=msg.getText().toString();

        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getPush.php";
        JSONArray products = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));

        params.add(new BasicNameValuePair("people[]", userConfig.userID));
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        adapter.replaceAll(TestDataClub.getTestAdData());
        msg.setText("");
    }

//    private void initData() {
//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                content = s.toString().trim();
//            }
//        });
//
//        tvSend.setOnClickListener(new View.OnClickListener() {
//            @TargetApi(Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                ArrayList<ItemModel> data = new ArrayList<>();
//                ChatModel model = new ChatModel();
//                model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
//                model.setContent(content);
//                data.add(new ItemModel(ItemModel.CHAT_B, model));
//                adapter.addAll(data);
//                et.setText("");
//                hideKeyBorad(et);
//            }
//        });
//
//    }
//
//    private void hideKeyBorad(View v) {
//        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//        }
//    }

}
