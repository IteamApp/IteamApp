package iteamapp.iteamapp.FreeTimeTableUI;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import iteamapp.iteamapp.R;

/**
 * Created by wan on 2016/10/16.
 * GridView的适配器
 */
public class AbsGridAdapter extends BaseAdapter {

    private Context mContext;

    private String[][] contents;

    private int rowTotal;

    private int columnTotal;

    private int positionTotal;

    public AbsGridAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return positionTotal;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;
        if(contents[row][column].equals(""))
            return "";
        else
            return contents[row][column].substring(0,contents[row][column].length()-1);

    }

    public Object getId(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;
        if(contents[row][column].equals(""))
            return "";
        else
            return contents[row][column].substring(contents[row][column].length()-1);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grib_item, null);
        }
        final TextView text = (TextView) convertView.findViewById(R.id.text);
        final TextView tvid = (TextView) convertView.findViewById(R.id.gird_freetime_id);
        //如果有课,那么添加数据

//        if( !getItem(position).equals("")) {
//            text.setText((String)getItem(position));
//            text.setTextColor(Color.WHITE);
//
        //变换颜色text.setBackground(mContext.getResources().getDrawable(R.drawable.text.setText("");
        //text.setTextColor(Color.WHITE);
       // text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_none));));
//            int rand = position % columnTotal;
//            switch( rand ) {
//                case 0:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.grid_item_bg));
//                    break;
//                case 1:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_12));
//                    break;
//                case 2:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_13));
//                    break;
//                case 3:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_14));
//                    break;
//                case 4:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_15));
//                    break;
//                case 5:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_16));
//                    break;
//                case 6:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_17));
//                    break;
//                case 7:
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_18));
//                    break;
//            }
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("1","111111");
//                    text.setText("");
//                    text.setTextColor(Color.WHITE);
//                    text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_none));
////                    int row = position / columnTotal;
////                    int column = position % columnTotal;
////                    String con = "当前选中的是" + contents[row][column] + "课";
////                    Toast.makeText(mContext, con, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


        text.setText((String)getItem(position));
        text.setTextColor(Color.WHITE);

        tvid.setText(getId(position).toString());
        if(!text.getText().toString().equals("")){
            text.setBackground(mContext.getResources().getDrawable(R.drawable.grid_item_bg));
        }
        if(!tvid.getText().toString().equals("2")) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!text.getText().toString().equals("")) {

                        text.setText("");
                        //text.setTextColor(Color.WHITE);
                        text.setBackground(mContext.getResources().getDrawable(R.drawable.bg_none));
                        int row = position / columnTotal;
                        int column = position % columnTotal;
                        contents[row][column]="";
//                    String con = "当前选中的是" +row+"  "+column;
//                    Toast.makeText(mContext, con, Toast.LENGTH_SHORT).show();
                    } else {
                        text.setText("有课");
                        int row = position / columnTotal;
                        int column = position % columnTotal;
                        contents[row][column]="有课";
                        text.setTextColor(Color.WHITE);
                        text.setBackground(mContext.getResources().getDrawable(R.drawable.grid_item_bg));
                    }
                }
            });
        }
        return convertView;
    }

    /**
     * 设置内容、行数、列数
     */
    public void setContent(String[][] contents, int row, int column) {
        this.contents = contents;
        this.rowTotal = row;
        this.columnTotal = column;
        positionTotal = rowTotal * columnTotal;
    }


}
