package iteamapp.iteamapp.Tools;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by zqx on 2017/8/5.
 */

public class ToastTool {

    public static  void show(Context context, String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
