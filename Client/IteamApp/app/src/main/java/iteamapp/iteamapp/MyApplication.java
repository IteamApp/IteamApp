package iteamapp.iteamapp;
import android.app.Application;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by HDL on 2016/9/8.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化sdk

        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了

    }
}
