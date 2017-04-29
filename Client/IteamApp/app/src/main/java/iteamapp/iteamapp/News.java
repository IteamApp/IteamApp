package iteamapp.iteamapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ce on 2017/4/29.
 */

public class News extends Activity {
    private Button btn1;
    private TextView text1;
    private ArrayAdapter<String> arr_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news);

        btn1 = (Button)findViewById(R.id.btn1);
        text1 = (TextView)findViewById(R.id.text1);
        String[]arr_data = {"andriod1"};
        arr_adapter = new ArrayAdapter<String>(this,R.layout.item_news,arr_data);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
