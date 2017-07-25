package iteamapp.iteamapp.FreeTimeTableUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import iteamapp.iteamapp.ClubDetail;
import iteamapp.iteamapp.FreeTimePerson;
import iteamapp.iteamapp.R;
import iteamapp.iteamapp.StarList;
import iteamapp.iteamapp.Tools.TeamConfig;

/**
 * Created by wan on 2016/10/16.
 * GridView的适配器
 */
public class AbsGridAdapterClub extends BaseAdapter {

    private Context mContext;

    private String[][] contents;

    private int rowTotal;

    private int columnTotal;

    private int positionTotal;

    public AbsGridAdapterClub(Context context) {
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


        text.setText((String)getItem(position));
        text.setTextColor(Color.WHITE);

        tvid.setText(getId(position).toString());
        if(!text.getText().toString().equals("")){
            text.setBackground(mContext.getResources().getDrawable(R.drawable.grid_item_bg));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int row = position / columnTotal;
                int column = position % columnTotal;
                String time=(column+1)+"."+(row+1);
                Intent in = new Intent(((Activity) mContext), FreeTimePerson.class);
                in.putExtra("time",time);
                mContext.startActivity(in);
                ((Activity) mContext).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
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
