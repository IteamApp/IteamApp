package iteamapp.iteamapp.adapter;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AppAplication extends Application {

    private static Context mContext;
    public static DisplayImageOptions mOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initImageLoader(this);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(200 * 1024 * 1024) // 200 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        System.out.println("hhhhhh");
        ImageLoader.getInstance().init(config);


        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(iteamapp.iteamapp.R.mipmap.ic_loading_large)   //加载过程中
                .showImageForEmptyUri(iteamapp.iteamapp.R.mipmap.ic_loading_large) //uri为空时
                .showImageOnFail(iteamapp.iteamapp.R.mipmap.ic_loading_large)      //加载失败时
                .cacheOnDisk(true)
                .cacheInMemory(true)                             //允许cache在内存和磁盘中
                .bitmapConfig(Bitmap.Config.RGB_565)             //图片压缩质量参数
                .build();
    }
}