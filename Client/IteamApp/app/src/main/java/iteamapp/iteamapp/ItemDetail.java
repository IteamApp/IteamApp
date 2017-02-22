package iteamapp.iteamapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by zqx on 2017/2/20.
 */

public class ItemDetail extends Activity {


    private ImageView mBack;
    private ImageView mMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        mBack = (ImageView) findViewById(R.id.menu_back);
        mMore = (ImageView) findViewById(R.id.menu_more);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}