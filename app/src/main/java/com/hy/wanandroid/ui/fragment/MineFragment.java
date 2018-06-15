package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hy.wanandroid.R;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.ui.activity.AboutActivity;
import com.hy.wanandroid.ui.activity.ArticleListActivity;
import com.hy.wanandroid.ui.activity.CollectListActivity;
import com.hy.wanandroid.ui.activity.LoginActivity;
import com.hy.wanandroid.ui.activity.SettingActivity;
import com.hy.wanandroid.ui.activity.WebActivity;

import org.w3c.dom.Text;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 我的     /   个人中心
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    TextView userName;
    TextView myCollection;
    TextView setting;
    TextView about;
    TextView authorTv;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void viewInit() {
        userName = getContentView().findViewById(R.id.userName);
        myCollection = (TextView) getContentView().findViewById(R.id.myCollection);
        myCollection.setOnClickListener(this);
        setting = (TextView) getContentView().findViewById(R.id.setting);
        setting.setOnClickListener(this);
        about = (TextView) getContentView().findViewById(R.id.about);
        about.setOnClickListener(this);
        authorTv = (TextView) getContentView().findViewById(R.id.authorTv);
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否可以点击
        if (TextUtils.isEmpty(SharedPreferenceUtils.ReadUsername())) {
            userName.setOnClickListener(this);
            userName.setText(R.string.not_login_txt);
        } else {
            userName.setText(SharedPreferenceUtils.ReadUsername());
        }
    }

    @Override
    public void dataInit() {

        SpannableString spanText = new SpannableString("作者:huxiaozi");
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(LINK, getString(R.string.author_link_txt));
                startActivity(intent);
            }
        }, 3, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        authorTv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        authorTv.setText(spanText);
        authorTv.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userName:
                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.myCollection:
                Intent intentCollect = new Intent(getActivity(), CollectListActivity.class);
                startActivity(intentCollect);
                break;
            case R.id.setting:
                Intent intentSetting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.about:
                Intent intentAbout = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
    }
}
