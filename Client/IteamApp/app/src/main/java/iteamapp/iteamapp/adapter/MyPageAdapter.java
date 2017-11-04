package iteamapp.iteamapp.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import iteamapp.iteamapp.News;
import iteamapp.iteamapp.R;
import iteamapp.iteamapp.Tools.IpConfig;
import iteamapp.iteamapp.Tools.JSONParser;
import iteamapp.iteamapp.Tools.TeamConfig;
import iteamapp.iteamapp.Tools.newsType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.viewpagerindicator.CirclePageIndicator;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqx on 2017/2/20.
 */

public  class MyPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG_PID = "pid";

    private Context context;
    private String userID;
    public List<String> nameDatas;
    public List<String> infoDatas;
    public ArrayList<ImageView> imageList;
    public  List<String> idDatas;
    public  List<String> imgDatas;
    public  List<String> logoDatas;
    public  List<String> timeDatas;
    private static final int HEAD_VIEW = 0;//头布局
    private static final int BODY_VIEW = 2;//内容布局
    private static final int TAG_VIEW = 1;//内容布局
    private CustomPopWindow mCustomPopWindow;
    private RadioButton mButton3;
    private RadioGroup rgGroup;
    private int Flag=0;
    private Boolean no=false;

    private ProgressDialog pDialog;
    private ImageLoader imageLoader;
    IpConfig ip = new IpConfig();
    JSONParser jParser = new JSONParser();
    private  String url = ip.ip+"android/zqx/getArticle.php";
    JSONArray products = null;
    private DisplayImageOptions displayImageOptions;

    private MyAdapter mPagerAdapter = new MyAdapter();
    public MyPageAdapter(Context context,String userID){
        this.context = context;
        this.userID=userID;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

//    //动画加载
//    private void setAnimation(View viewToAnimate, int position) {
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
//                    .anim.item_bottom_in);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
//
//    @Override
//    public void onViewDetachedFromWindow(ViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//
//        holder.card.clearAnimation();
//
//    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_VIEW) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.headview_recycleview, parent, false);
            MyHeadViewHolder viewHolder = new MyHeadViewHolder(view);
            return viewHolder;
        }

        if (viewType == TAG_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.girdview, parent, false);
            MyTabViewHolder viewHolder = new MyTabViewHolder(view);
            return viewHolder;
        }

        if (viewType == BODY_VIEW) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycle, parent, false);
            MyBodyViewHolder viewHolder = new MyBodyViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将数据填充到具体的view中
        if (holder instanceof MyHeadViewHolder) {
            ((MyHeadViewHolder) holder).mViewPager.setAdapter(mPagerAdapter);
            ((MyHeadViewHolder) holder).indicator.onPageSelected(0);
            ((MyHeadViewHolder) holder).indicator.setViewPager(((MyHeadViewHolder) holder).mViewPager);
            ((MyHeadViewHolder) holder).indicator.setSnap(true);
        }
        if (holder instanceof MyTabViewHolder) {
            rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(Flag==0) {
                        if (i == R.id.club_mine) {
                            newsType.type=1;
                            new LoadAllArticle(userID,"1").execute();
                        }
                        if (i == R.id.club_hot) {
                            newsType.type=2;
                            new LoadAllArticle(userID,"2").execute();
                        }
                        if (i == R.id.club_shaixuan) {
                            View contentView = LayoutInflater.from(context).inflate(R.layout.pop_menu, null);
                            //处理popWindow 显示内容
                            handleLogic(contentView);
                            //创建并显示popWindow
                            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                                    .setView(contentView)
                                    .create()
                                    .showAsDropDown(mButton3, 0, 20);
                        }
                    }
                    else {
                        Flag=0;
                    }
                }
            });
        }
        if (holder instanceof MyBodyViewHolder) {
            if(no==true){

                ((MyBodyViewHolder) holder).tvno.setText("还没有社团信息，快去逛逛吧~~~");
                ((MyBodyViewHolder) holder).tvno.setVisibility(View.VISIBLE);
                ((MyBodyViewHolder) holder).tv.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).tvinfo.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).tvmore.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).img_article.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).imglogo.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).tvid.setVisibility(View.INVISIBLE);
                no=false;
            }
            else {
                ((MyBodyViewHolder) holder).tvno.setText("");
                ((MyBodyViewHolder) holder).tvno.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).tv.setVisibility(View.VISIBLE);
                ((MyBodyViewHolder) holder).tvinfo.setVisibility(View.VISIBLE);
                ((MyBodyViewHolder) holder).tvmore.setVisibility(View.VISIBLE);
                ((MyBodyViewHolder) holder).img_article.setVisibility(View.VISIBLE);
                ((MyBodyViewHolder) holder).imglogo.setVisibility(View.VISIBLE);
                ((MyBodyViewHolder) holder).tvid.setVisibility(View.INVISIBLE);
                ((MyBodyViewHolder) holder).tv.setText(nameDatas.get(position - 2));
                ((MyBodyViewHolder) holder).tvinfo.setText(timeDatas.get(position - 2));
                ((MyBodyViewHolder) holder).tvmore.setText(infoDatas.get(position - 2));
                imageLoader.displayImage(imgDatas.get(position - 2),((MyBodyViewHolder) holder).img_article, displayImageOptions);
               // imageLoader.displayImage(imgDatas.get(position - 2), ((MyBodyViewHolder) holder).img_article, AppAplication.mOptions);
               //((MyBodyViewHolder) holder).img_article.setImageBitmap(returnBitMap(imgDatas.get(position - 2)));
                //((MyBodyViewHolder) holder).imglogo.setImageBitmap(returnBitMap(logoDatas.get(position - 2)));
                imageLoader.displayImage(logoDatas.get(position - 2),((MyBodyViewHolder) holder).imglogo, displayImageOptions);
                ((MyBodyViewHolder) holder).tvid.setText(idDatas.get(position - 2));
            }

        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (position > 1) {
                        String pid = idDatas.get(position - 2);
                        Intent in = new Intent(((Activity) context), News.class);
                        in.putExtra(TAG_PID, pid);
                        context.startActivity(in);
                        ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }


            });

    }


    private void initData(String usercode,String type){

        idDatas.clear();
        nameDatas .clear();
        infoDatas = new ArrayList<String>();
        logoDatas = new ArrayList<String>();
        imgDatas = new ArrayList<String>();
        timeDatas = new ArrayList<String>();


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", usercode));
        params.add(new BasicNameValuePair("type", type));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            int success = json.getInt("success");
            if (success == 0) {
                no = true;
            } else {
                no=false;
                // products found
                // Getting Array of Products
                products = json.getJSONArray("article");

                // looping through All Products
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                    // Storing each json item in variable
                    nameDatas.add(c.getString("team_name"));
                    infoDatas.add(c.getString("passage_content"));
                    timeDatas.add(c.getString("passage_time"));
                    imgDatas.add("http://123.206.61.96:8088/android/zqx/" + c.getString("passage_picture"));
                    idDatas.add(c.getString("id"));
                    logoDatas.add("http://123.206.61.96:8088/android/zqx/" + c.getString("team_logo"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllArticle extends AsyncTask<String, String, String> {

        String usercode;
        String type;

        LoadAllArticle(String usercode,String type){
            this.usercode=usercode;
            this.type=type;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("正在加载，请稍后...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            initData(usercode,type);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            notifyDataSetChanged();
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }

    class LoadAll extends AsyncTask<String, String, String> {

        String faculty;

        LoadAll(String faculty){
            this.faculty=faculty;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("正在加载，请稍后...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            mydata(faculty);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            notifyDataSetChanged();
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }


    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "全部社团";
                        newsType.type=3;
                        new LoadAll("").execute();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;

                    case R.id.menu2:
                        showContent = "信息学院";
                        newsType.type=4;
                        new LoadAll("2").execute();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;

                    case R.id.menu3:
                        showContent = "法政学院";
                        newsType.type=5;
                        new LoadAll("3").execute();
                        Flag=1;
                        rgGroup.clearCheck();
                        break;
                }
                Toast.makeText(context,showContent,Toast.LENGTH_SHORT).show();

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
    }

    private void mydata(String faculty) {
        IpConfig ip = new IpConfig();
        JSONParser jParser = new JSONParser();
        String url = ip.ip+"android/zqx/ArticleFaculty.php";
        JSONArray products = null;


        idDatas.clear();
        nameDatas .clear();
        infoDatas = new ArrayList<String>();
        logoDatas = new ArrayList<String>();
        imgDatas = new ArrayList<String>();
        timeDatas = new ArrayList<String>();


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", faculty));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

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
                nameDatas.add(c.getString("team_name"));
                infoDatas.add(c.getString("passage_content"));
                timeDatas.add(c.getString("passage_time"));
                imgDatas.add("http://123.206.61.96:8088/android/zqx/"+c.getString("passage_picture"));
                idDatas.add(c.getString("id"));
                logoDatas.add("http://123.206.61.96:8088/android/zqx/"+c.getString("team_logo"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        if(no==true)
            return 3;
        else
            return nameDatas.size() + 2;
    }

    //如果是第一项，则加载头布局
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        } else if(position==1) {
            return TAG_VIEW;
        }
        else {return BODY_VIEW;}
    }

    //头布局的viewholder
    class MyHeadViewHolder extends RecyclerView.ViewHolder {
        ViewPager mViewPager;
        CirclePageIndicator indicator;

        public MyHeadViewHolder(View itemView) {
            super(itemView);
            mViewPager = (ViewPager) itemView.findViewById(R.id.vp_tab_headview);
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
        }
    }

    class MyTabViewHolder extends RecyclerView.ViewHolder {

        public MyTabViewHolder(View itemView) {
            super(itemView);
            rgGroup = (RadioGroup) itemView.findViewById(R.id.club_group);
            mButton3 = (RadioButton) itemView.findViewById(R.id.club_shaixuan);
        }
    }
    class MyBodyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tvinfo;
        TextView tvid;
        ImageView img_article;
        TextView tvmore;
        TextView tvno;
        ImageView imglogo;

        public MyBodyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.recycle_tv);
            tvinfo = (TextView) itemView.findViewById(R.id.recycle_info);
            tvid = (TextView) itemView.findViewById(R.id.recycle_pid);
            img_article = (ImageView) itemView.findViewById(R.id.img_article);
            tvmore= (TextView) itemView.findViewById(R.id.recycle_more);
            tvno= (TextView) itemView.findViewById(R.id.no_people);
            imglogo= (ImageView) itemView.findViewById(R.id.recycle_img);

        }
    }

    //viewpager的adapter
    class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
            return imageList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Integer.toString(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
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