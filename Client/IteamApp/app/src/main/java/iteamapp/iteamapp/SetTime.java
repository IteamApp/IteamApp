package iteamapp.iteamapp;

import android.app.*;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;

/**
 * Created by zqx on 2017/8/2.
 */

public class SetTime extends Activity {
    private Button start;
    private Button end;

    private Button submit;
    private TextView title;
    private ImageView mBack;
    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;
    private String type = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.time_set);
        start = (Button) findViewById(R.id.btn_start);
        end = (Button) findViewById(R.id.btn_end);
        submit = (Button) findViewById(R.id.btn_time_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        title = (TextView) findViewById(R.id.common_title);
        title.setText("设置报名时间");
        mBack = (ImageView) findViewById(R.id.common_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "start";
                showDialog(DATE_DIALOG);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "end";
                showDialog(DATE_DIALOG);
            }
        });


        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        if (type.equals("start"))
            start.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
        else
            end.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));

    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    private void initData() throws JSONException {
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/getTime.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("start", ""));
        params.add(new BasicNameValuePair("end", ""));
        params.add(new BasicNameValuePair("type", "2"));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);
        int success = json.getInt("success");

        if (success == 1) {
            String startStr = json.getString("start");

            String[] startArray = startStr.split("-");
            String endStr = json.getString("end");
            String[] endArray = endStr.split("-");

            start.setText(new StringBuffer().append(startArray[0]).append("-").append(startArray[1]).append("-").append(startArray[2]));
            end.setText(new StringBuffer().append(endArray[0]).append("-").append(endArray[1]).append("-").append(endArray[2]));

        } else {
            end.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
            start.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
        }

    }


    private void submitData() throws ParseException {
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/getTime.php";

        String satrtStr = start.getText().toString();
        String endStr = end.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-ss");
        Date start = sdf.parse(satrtStr);
        Date end = sdf.parse(endStr);
        if (start.after(end)) {
            Toast.makeText(SetTime.this, "开始时间不可在结束时间之前", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("start", satrtStr));
        params.add(new BasicNameValuePair("end", endStr));
        params.add(new BasicNameValuePair("type", "1"));
        //params.add(new BasicNameValuePair("brief", introduction.getText().toString()));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        String showContent = "保存成功";
        Toast.makeText(SetTime.this, showContent, Toast.LENGTH_SHORT).show();

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SetTime Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
