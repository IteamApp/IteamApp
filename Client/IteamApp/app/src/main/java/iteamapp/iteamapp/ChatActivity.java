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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import iteamapp.iteamapp.R;
import iteamapp.iteamapp.TestData;
import iteamapp.iteamapp.adapter.ChatAdapter;
import iteamapp.iteamapp.model.ChatModel;
import iteamapp.iteamapp.model.ItemModel;

public class ChatActivity extends Activity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private String content;
    private ImageView mBack;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        title= (TextView) findViewById(R.id.top_msg_title);
        title.setText("消息详情");
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
        adapter.replaceAll(TestData.getTestAdData());
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
