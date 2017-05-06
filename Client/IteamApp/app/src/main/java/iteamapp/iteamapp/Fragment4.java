package iteamapp.iteamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment4 extends Fragment {

    private Button txtFreeTime;

    private View view;

    private TextView star;  //关注
    private TextView club;
    private TextView signup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);
        txtFreeTime = (Button) view.findViewById(R.id.txtFreeTime);
        txtFreeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(),FreeTimeTable.class);
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        //点击关注 打开关注列表
        star = (TextView) view.findViewById(R.id.starNum);
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        club = (TextView) view.findViewById(R.id.teamNum);
        club.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        signup = (TextView) view.findViewById(R.id.signNum);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StarList.class);

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        return view;

    }


}
