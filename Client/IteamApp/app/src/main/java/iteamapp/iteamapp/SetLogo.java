package iteamapp.iteamapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.androidrichtexteditor.FileUtils;


public class SetLogo extends Activity {

    private static final int REQUEST_CODE_WRITE_CONTACTS = 1;
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
    private final int REQUEST_CODE_PICK_IMAGE = 200;
    private File mCameraImageFile;// 照相机拍照得到的图片
    private FileUtils mFileUtils;
    private String ROLE = "add";// 当前页面是新增还是查看详情 add/modify
    private TextView title;

    private Button bt;
    private Button bt_take;
    private Button bt_submit;
    private ImageView mBack;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private ImageView photo;
    private Uri imageUri;
    private File tempFile;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setlogo);
        photo = (ImageView) findViewById(R.id.set_userImg);
        mFileUtils = new FileUtils(this);
        title = (TextView) findViewById(R.id.common_title);
        title.setText("设置头像");
        mBack = (ImageView) findViewById(R.id.common_menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        bt_submit = (Button) findViewById(R.id.btn_photo_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLogo(type);
            }
        });
        final Activity activity=this;
        bt = (Button) findViewById(R.id.btn_photo);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
                // 激活系统图库，选择一张图
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });
        bt_take = (Button) findViewById(R.id.btn_camera);
        bt_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(activity);
            }
        });
        InitData(type);
    }

    private void submitLogo(String type) {
        String userId = "";
        if (type.equals("1")) {
            userId = userConfig.userID;
        } else {
            userId = TeamConfig.TeamID;
        }
        Bitmap bm = ((BitmapDrawable) photo.getDrawable()).getBitmap();
        //压缩图片
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);


        String b64 = bitmapToBase64(bm);
        Log.d("b64", b64);
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip + "android/zqx/updateLogo.php";

        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("id", userId));
        params.add(new BasicNameValuePair("picture", b64));

        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "POST", params);

        String showContent = "上传成功！";
        Toast.makeText(SetLogo.this, showContent, Toast.LENGTH_SHORT).show();

    }

    private void InitData(String type) {
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = "";
        String userId = "";
        String logotype = "";
        JSONArray products = null;
        if (type.equals("1")) {
            url = ip.ip + "android/zqx/getUserInfo.php";
            userId = userConfig.userID;
            logotype = "user_head";
        } else {
            url = ip.ip + "android/zqx/teamDetail.php";
            userId = TeamConfig.TeamID;
            logotype = "team_logo";
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", userId));
        params.add(new BasicNameValuePair("team_id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);
        // Check your log cat for JSON reponse


        try {
            photo.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/" + json.getString(logotype)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Bitmap returnBitMap(String url) {
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


//    private void openCamera() {
//        try {
//            File PHOTO_DIR = new File(mFileUtils.getStorageDirectory());
//            if (!PHOTO_DIR.exists())
//                PHOTO_DIR.mkdirs();// 创建照片的存储目录
//
//            mCameraImageFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
//            final Intent intent = getTakePickIntent(mCameraImageFile);
//            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
//        } catch (ActivityNotFoundException e) {
//        }
//    }

    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.class)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
    }

    /*
* 判断sdcard是否被挂载
*/
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoOutputUri = FileProvider.getUriForFile(
                getApplicationContext(), "iteamapp.iteamapp.fileprovider", f);
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
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                Log.d("uri", uri + "");
                crop(uri);
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
//            Log.d("ppppppp",mCameraImageFile.getAbsolutePath());
//            photo.setImageBitmap(getSDCardImg(mCameraImageFile.getAbsolutePath()));
            crop(imageUri);
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                photo.setImageBitmap(bitmap);
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

    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     *
     * @param imagePath 图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
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


}
