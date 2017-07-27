package iteamapp.iteamapp.androidrichtexteditor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iteamapp.iteamapp.R;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.userConfig;
import iteamapp.iteamapp.enroll;
import iteamapp.iteamapp.runtimepermissions.PermissionsManager;
import iteamapp.iteamapp.runtimepermissions.PermissionsResultAction;

@SuppressLint("SimpleDateFormat")
public class RichTextActivity extends Activity implements OnClickListener {
    private static final int REQUEST_CODE_WRITE_CONTACTS =1 ;
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
	private final int REQUEST_CODE_PICK_IMAGE = 200;
	private final String TAG = "RichTextActivity";
	private Context context;
	private LinearLayout line_rootView, line_addImg;
	private InterceptLinearLayout line_intercept;
	private RichTextEditor richText;
	private EditText et_name;
	private TextView  tv_title, tv_ok;
    ImageView tv_back;
	private boolean isKeyBoardUp, isEditTouch;// 判断软键盘的显示与隐藏
	private File mCameraImageFile;// 照相机拍照得到的图片
	private FileUtils mFileUtils;
	private String ROLE = "add";// 当前页面是新增还是查看详情 add/modify

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_selectimg);

        /**
         * 请求所有必要的权限----android6.0必须要动态申请权限,否则选择照片和拍照功能 用不了哦
         */
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {//权限通过了
            }

            @Override
            public void onDenied(String permission) {//权限拒绝了

            }
        });
		context = this;
		init();
	}

	private void init() {
		if (getIntent() != null) {
			Bundle bundle = new Bundle();
			bundle = this.getIntent().getExtras();
			ROLE =bundle.getString("role");
		}

		mFileUtils = new FileUtils(context);

		line_addImg = (LinearLayout) findViewById(R.id.line_addImg);
		line_intercept = (InterceptLinearLayout) findViewById(R.id.line_intercept);
        line_rootView = (LinearLayout) findViewById(R.id.line_rootView);

		tv_back = (ImageView) findViewById(R.id.add_menu_back);
		tv_ok = (TextView) findViewById(R.id.activity_selectimg_send);
		et_name = (EditText) findViewById(R.id.et_name);
		richText = (RichTextEditor) findViewById(R.id.richText);
		initRichEdit();
		if ("modify".equals(ROLE)) {
			tv_ok.setText("修改");
			tv_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Bundle bundle = new Bundle();
						bundle = getIntent().getExtras();
						String id =bundle.getString("id");


						IpConfig ip = new IpConfig();
						JSONParser jParser = new JSONParser();
						String url = ip.ip+"android/zqx/updateArticle.php";

						JSONArray products = null;
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", id));
						params.add(new BasicNameValuePair("title", et_name.getText().toString()));
						params.add(new BasicNameValuePair("content", richText.getRichEditData().get("text").toString()));
						params.add(new BasicNameValuePair("picture", imageToBase64(richText.getRichEditData().get("imgUrls").toString())));
						// getting JSON string from URL
						JSONObject json = jParser.makeHttpRequest(url, "GET", params);

						String showContent = "修改成功！";
						Toast.makeText(RichTextActivity.this,showContent,Toast.LENGTH_SHORT).show();

						finish();

//					Log.i(TAG, "---richtext-data:" + richText.getRichEditData());
					} catch (IOException e) {
						e.printStackTrace();
					}
//				Toast.makeText(context, "信息已打印,请到控制台查看", Toast.LENGTH_LONG)
//						.show();
				}
			});
			line_intercept.setIntercept(true);
			richText.setIntercept(true);
			getData();
		} else {
			tv_ok.setText("提交");
			tv_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {

						IpConfig ip = new IpConfig();
						JSONParser jParser = new JSONParser();
						String url = ip.ip+"android/zqx/AddArticle.php";

						JSONArray products = null;
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", TeamConfig.TeamID));
						params.add(new BasicNameValuePair("title", et_name.getText().toString()));
						params.add(new BasicNameValuePair("content", richText.getRichEditData().get("text").toString()));
						params.add(new BasicNameValuePair("picture", imageToBase64(richText.getRichEditData().get("imgUrls").toString())));
						// getting JSON string from URL
						JSONObject json = jParser.makeHttpRequest(url, "GET", params);

						String showContent = "发布成功！";
						Toast.makeText(RichTextActivity.this,showContent,Toast.LENGTH_SHORT).show();

						finish();

//					Log.i(TAG, "---richtext-data:" + richText.getRichEditData());
					} catch (IOException e) {
						e.printStackTrace();
					}
//				Toast.makeText(context, "信息已打印,请到控制台查看", Toast.LENGTH_LONG)
//						.show();
				}
			});
		}
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * @将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @author QQ986945193
	 * @Date 2015-01-26
	 * @param path 图片路径
	 * @return
	 */
	public static String imageToBase64(String path) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		// 读取图片字节数组
		try {

			InputStream in = new FileInputStream(path);

			data = new byte[in.available()];

			in.read(data);

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编
		// 返回Base64编码过的字节数组字符串
		return Base64.encodeToString(data,Base64.DEFAULT);
	}



	private void initRichEdit() {
		ImageView img_addPicture, img_takePicture;
		img_addPicture = (ImageView) line_addImg
				.findViewById(R.id.img_addPicture);
		img_addPicture.setOnClickListener(this);
		img_takePicture = (ImageView) line_addImg
				.findViewById(R.id.img_takePicture);
		img_takePicture.setOnClickListener(this);

		et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					isEditTouch = false;
					line_addImg.setVisibility(View.GONE);
				}
			}

		});
		richText.setLayoutClickListener(new RichTextEditor.LayoutClickListener() {
			@Override
			public void layoutClick() {
				isEditTouch = true;
				line_addImg.setVisibility(View.VISIBLE);
			}
		});

		line_rootView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						int heightDiff = line_rootView.getRootView()
								.getHeight() - line_rootView.getHeight();
						if (isEditTouch) {
							if (heightDiff > 500) {// 大小超过500时，一般为显示虚拟键盘事件,此判断条件不唯一
								isKeyBoardUp = true;
								line_addImg.setVisibility(View.VISIBLE);
							} else {
								if (isKeyBoardUp) {
									isKeyBoardUp = false;
									isEditTouch = false;
									line_addImg.setVisibility(View.GONE);
								}
							}
						}
					}
				});
	}

	private void getData() {
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

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
				richText.insertText(c.getString("passage_content"));
				et_name.setText(c.getString("passage_title"));
				richText.insertImageByURL("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == REQUEST_CODE_PICK_IMAGE) {
			Uri uri = data.getData();
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                //申请 WRITE_CONTACTS 权限
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_CODE_WRITE_CONTACTS);
//            }
			richText.insertImage(mFileUtils.getFilePathFromUri(uri));
		} else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA
				&& resultCode == Activity.RESULT_OK) {
			richText.insertImage(mCameraImageFile.getAbsolutePath());
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mFileUtils.deleteRichTextImage();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_addPicture:
			// 打开系统相册
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");// 相片类型
			startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
			break;
		case R.id.img_takePicture:
			// 打开相机
			openCamera();
			break;

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

}
