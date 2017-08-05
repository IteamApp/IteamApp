package iteamapp.iteamapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.model.ChatModel;
import iteamapp.iteamapp.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by：Administrator on 2015/12/21 16:43
 */
public class TestData {

    public static ArrayList<ItemModel> getTestAdData() {
        ArrayList<ItemModel> models = new ArrayList<>();


        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getmsg.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userConfig.userID));
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));

        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        try {
            // products found
            // Getting Array of Products
            products = json.getJSONArray("msg");
            // looping through All Products
            for (int i = 0; i < products.length(); i++) {
                ChatModel model = new ChatModel();
                JSONObject c = products.getJSONObject(i);
                // Storing each json item in variable
                model.setContent(c.getString("message_title"));
                model.setTime(c.getString("message_time"));
                model.setIcon("http://123.206.61.96:8088/android/zqx/"+c.getString("team_logo"));
                models.add(new ItemModel(ItemModel.CHAT_A, model));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return models;
    }


}
