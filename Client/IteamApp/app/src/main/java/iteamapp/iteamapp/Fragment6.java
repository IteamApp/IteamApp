package iteamapp.iteamapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import iteamapp.iteamapp.Tools.ToastTool;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.androidrichtexteditor.FileUtils;
import iteamapp.iteamapp.runtimepermissions.PermissionsManager;
import iteamapp.iteamapp.runtimepermissions.PermissionsResultAction;
import iteamapp.iteamapp.utils.CustomDiaLog;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment6 extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{
//    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
//    private static final int PHOTO_REQUEST_CUT = 3;// 结果
//    private ImageView photo,photo2;
//    private File tempFile;
//    private static final int REQUEST_CODE_WRITE_CONTACTS =1 ;
//    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
//    private final int REQUEST_CODE_PICK_IMAGE = 200;
//    private File mCameraImageFile;// 照相机拍照得到的图片
//    private FileUtils mFileUtils;
//    private String ROLE = "add";// 当前页面是新增还是查看详情 add/modify


    private LinearLayout txtFreeTime;
    private LinearLayout topersonal;
    private View view;
    private LinearLayout time;  //关注
    private LinearLayout club;
    private LinearLayout signup;

    private TextView teamNum;
    private TextView signNum;

    private TextView username;
    private ImageView userimg;
    private Button btnExit;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Boolean isFirst=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4_club, container, false);
        txtFreeTime = (LinearLayout) view.findViewById(R.id.txtFreeTime_club);
        username= (TextView) view.findViewById(R.id.userName_club);
        userimg= (ImageView) view.findViewById(R.id.userImg_club);
        signNum=(TextView) view.findViewById(R.id.signNum_club);
        teamNum= (TextView) view.findViewById(R.id.teamNum_club);
//        mFileUtils = new FileUtils(getActivity());
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(getActivity(), new PermissionsResultAction() {
            @Override
            public void onGranted() {//权限通过了
            }

            @Override
            public void onDenied(String permission) {//权限拒绝了

            }
        });
        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(((Activity)getActivity()), SetLogo.class);
                in.putExtra("type","2");
                getContext().startActivity(in);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        btnExit= (Button) view.findViewById(R.id.exit_club);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String showContent = "退出成功";
                Toast.makeText(getContext(),showContent,Toast.LENGTH_SHORT).show();
                Intent in = new Intent(((Activity)getActivity()), LoginActivity.class);
                getContext().startActivity(in);
                ((Activity)getActivity()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        txtFreeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(getActivity(),FreeTimeTableClub.class);
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        club = (LinearLayout) view.findViewById(R.id.team_club);
        club.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!teamNum.getText().equals("0")) {

                    Intent intent = new Intent(getActivity(), StarListAction.class);
                    intent.putExtra("type", "4");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else{
                    ToastTool.show(getContext(),"还没有社团成员，快去宣传吧~");
                }
            }
        });

        signup = (LinearLayout) view.findViewById(R.id.sign_club);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!signNum.getText().equals("0")) {

                    Intent intent = new Intent(getActivity(), StarListAction.class);
                    intent.putExtra("type", "6");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else{
                    ToastTool.show(getContext(),"还没有人报名，快去宣传吧~");
                }
            }
        });

        topersonal=(LinearLayout)view.findViewById(R.id.topersonal_club);
        topersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), club_edit.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        time= (LinearLayout) view.findViewById(R.id.enroll_club);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetTime.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        teamNum.setText(InitMember("4"));
        signNum.setText(InitMember("6"));
        InitData();


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.my_swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isFirst) {
            teamNum.setText(InitMember("4"));
            signNum.setText(InitMember("6"));
            InitData();
        }
        isFirst=false;
    }

    private void InitData(){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/teamDetail.php";
        JSONArray products = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);
        // Check your log cat for JSON reponse


        try {
            username.setText(json.getString("team_name"));
            userimg.setImageBitmap(returnBitMap("http://123.206.61.96:8088/android/zqx/"+json.getString("team_logo")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String InitMember(String type){
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/getTeamMember.php";
        JSONArray products = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
        params.add(new BasicNameValuePair("type",type));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            int count=json.getInt("count");
            return count+"";

        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

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
//
//    private Intent getTakePickIntent(File f) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Uri photoOutputUri = FileProvider.getUriForFile(
//                getActivity().getApplicationContext(),"iteamapp.iteamapp.fileprovider", f);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
//        return intent;
//    }
//
//    /**
//     * 用当前时间给取得的图片命名
//     */
//    private String getPhotoFileName() {
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "'IMG'_yyyy_MM_dd_HH_mm_ss");
//        return dateFormat.format(date) + ".jpg";
//    }
//
//    public void showDailog() {
//        dialog = new CustomDiaLog(getActivity(), R.layout.dialog_evalute,
//                R.style.dialog, new CustomDiaLog.LeaveMyDialogListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                //System.out.println("aaaaaaaaaaaaaa");
//                switch (view.getId()) {
//                    case R.id.btn_takephoto:
//
//                        openCamera();
//                        dialog.dismiss();
//                        break;
//                    case R.id.btn_picture:
//                        // 打开系统相册
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/*");// 相片类型
//                        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
//                        dialog.dismiss();
//                        break;
//                    case R.id.btn_cancel:
//
//                        dialog.dismiss();
//                        break;
//
//                    default:
//                        break;
//                }
//
//            }
//        });
//        // 设置dialog弹出框显示在底部，并且宽度和屏幕一样
//        Window window = dialog.getWindow();
//        dialog.show();
//        window.setGravity(Gravity.BOTTOM);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.FILL_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//    }

    @Override
    public void onRefresh() {

        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                //initdata();
                teamNum.setText(InitMember("4"));
                signNum.setText(InitMember("6"));
                InitData();
            }
        }, 1000);

    }

}
