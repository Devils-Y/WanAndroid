package com.hy.wanandroid.ui.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.wanandroid.R;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 我的     /   个人中心
 */

public class MineFragment extends BaseFragment {
     @Override
     public int setContentLyaoutId() {
          return R.layout.fragment_mine;
     }

     @Override
     public void viewInit() {
          TextView myCollection = (TextView) getContentView().findViewById(R.id.myCollection);
          myCollection.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Toast.makeText(getActivity(),"这是我的收藏",Toast.LENGTH_SHORT).show();
               }
          });
          TextView setting = (TextView) getContentView().findViewById(R.id.setting);
          setting.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
          });
          TextView about = (TextView) getContentView().findViewById(R.id.about);
          about.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
          });
     }

     @Override
     public void dataInit() {

     }

     @Override
     public void viewDestroy() {

     }
}
