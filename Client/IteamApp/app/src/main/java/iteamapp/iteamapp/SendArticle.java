package iteamapp.iteamapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.androidrichtexteditor.FileUtils;
import iteamapp.iteamapp.androidrichtexteditor.RichTextActivity;
import iteamapp.iteamapp.utils.CustomDiaLog;
import iteamapp.iteamapp.utils.CustomDialogPhoto;

import static iteamapp.iteamapp.R.drawable.icon_addpic_focused;

/**
 * Created by zqx on 2017/8/4.
 */

public class SendArticle extends Activity {

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private ImageView photo,photo2;
    private File tempFile;
    private static final int REQUEST_CODE_WRITE_CONTACTS =1 ;
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
    private final int REQUEST_CODE_PICK_IMAGE = 200;
    private File mCameraImageFile;// 照相机拍照得到的图片
    private FileUtils mFileUtils;

    private ImageView mBack;
    private TextView tvTitle;
    private Button Send;
    private EditText content;
    private CustomDialogPhoto dialog;

    private ImageView addpic;
    private ImageView showpic;

    private String type="1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendarticle);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());


        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        final String role =bundle.getString("role");


        addpic= (ImageView) findViewById(R.id.add_pic);
        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("1"))
                    showDailog();
                else {
                    onBackPressed();
                }
            }
        });

        showpic= (ImageView) findViewById(R.id.article_show);

        mBack = (ImageView) findViewById(R.id.msg_back);
        tvTitle= (TextView) findViewById(R.id.title_msg);
        tvTitle.setText("发表文章");
        Send= (Button) findViewById(R.id.btn_send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals("add")){
                    submitData();
                }
                else {
                updateData();
                }
            }
        });
        content= (EditText) findViewById(R.id.msg_content);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(role.equals("add")){
            type="1";
        }
        else{
            tvTitle.setText("修改文章");
            Send.setText("提交");
            type="2";
            getData();
        }

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("您确定要删除吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addpic.setImageResource(icon_addpic_focused);
                        type="1";
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    /*
           * 剪切图片
           */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        }
        else  if(requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData();
        }
        else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
//                addpic.setVisibility(View.INVISIBLE);
//                showpic.setVisibility(View.VISIBLE);
                Bitmap bitmap = data.getParcelableExtra("data");
                addpic.setImageBitmap(bitmap);
                type="2";
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void updateData() {
        String b64="";

        if(!type.equals("1")) {


            Bitmap bm = ((BitmapDrawable) addpic.getDrawable()).getBitmap();
            //压缩图片
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            b64 = bitmapToBase64(bm);
        }


        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/updateArticle.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("title", ""));
        params.add(new BasicNameValuePair("content", content.getText().toString()));
        params.add(new BasicNameValuePair("picture", b64));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        String showContent = "修改成功！";
        Toast.makeText(SendArticle.this, showContent, Toast.LENGTH_SHORT).show();

        finish();

    }

    public void showDailog() {
        dialog = new CustomDialogPhoto(this, R.layout.dialog_photo,
                R.style.dialog, new CustomDialogPhoto.LeaveMyDialogListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btn_takephoto:

                        openCamera();
                        dialog.dismiss();
                        break;
                    case R.id.btn_picture:
                        // 打开系统相册
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");// 相片类型
                        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                        dialog.dismiss();
                        break;
                    case R.id.btn_cancel:

                        dialog.dismiss();
                        break;

                    default:
                        break;
                }

            }
        });
        // 设置dialog弹出框显示在底部，并且宽度和屏幕一样
        Window window = dialog.getWindow();
        dialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void openCamera() {
        try {
            File PHOTO_DIR = new File(mFileUtils.getStorageDirectory());
            if (!PHOTO_DIR.exists())
                PHOTO_DIR.mkdirs();// 创建照片的存储目录

            mCameraImageFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCameraImageFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (ActivityNotFoundException e) {
        }
    }

    private Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoOutputUri = FileProvider.getUriForFile(
               getApplicationContext(),"iteamapp.iteamapp.fileprovider", f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(date) + ".jpg";
    }

    private void getData() {


        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        String id =bundle.getString("id");


        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String urlcontent = ip.ip+"android/zqx/articleDetail.php";
        JSONArray products = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(urlcontent, "GET", params);
        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // products found
            // Getting Array of Products
            products = json.getJSONArray("article");
            // looping through All Products
            for (int i = 0; i < products.length(); i++) {
                JSONObject c = products.getJSONObject(i);
                // Storing each json item in variable
                content.setText(c.getString("passage_content"));
                Log.d("pic",c.getString("passage_picture"));
                if(c.getString("passage_picture").equals(""))
                    addpic.setImageResource(icon_addpic_focused);
                else
                  addpic.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     private  void submitData(){

         String b64="";

         if(!type.equals("1")) {


             Bitmap bm = ((BitmapDrawable) addpic.getDrawable()).getBitmap();
             //压缩图片
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
             b64 = bitmapToBase64(bm);
         }


            IpConfig ip = new IpConfig();
            JSONParser jParser = new JSONParser();
            String url = ip.ip+"android/zqx/AddArticle.php";

            JSONArray products = null;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
            params.add(new BasicNameValuePair("title", ""));
            params.add(new BasicNameValuePair("content", content.getText().toString()));
            params.add(new BasicNameValuePair("picture", b64));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

            String showContent = "发布成功！";
            Toast.makeText(SendArticle.this,showContent,Toast.LENGTH_SHORT).show();

            finish();
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
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
