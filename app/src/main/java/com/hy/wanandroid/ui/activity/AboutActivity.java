package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.hy.wanandroid.R;
import com.hy.wanandroid.util.VersionManagementUtil;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * author: huyin
 * date: 2018/6/15
 * <p>
 * 关于
 */
public class AboutActivity extends BaseActivity {

    Toolbar toolbar;
    TextView versionTv;
    TextView aboutNet;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.setting_app_txt);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void viewInit() {
        initTitle();

        versionTv = findViewById(R.id.versionTv);
        aboutNet = findViewById(R.id.aboutNet);
    }

    @Override
    public void dataInit() {
        versionTv.setText("版本 " + VersionManagementUtil.getVersion(this));

        aboutNet.setText("网站内容\n" +
                "        本网站每天新增20~30篇优质文章，并加入到现有分类中，力求整理出一份优质而又详尽的知识体系，闲暇时间不妨上来学习下知识； 除此以外，并为大家提供平时开发过程中常用的工具以及常用的网址导航。\n" +
                "\n" +
                "        当然这只是我们目前的功能，未来我们将提供更多更加便捷的功能...\n" +
                "\n" +
                "        如果您有任何好的建议:\n" +
                "\n" +
                "    —关于网站排版\n" +
                "    —关于新增常用网址以及工具\n" +
                "    —未来你希望增加的功能等\n" +
                "    可以在 https://github.com/hongyangAndroid/xueandroid 项目中以issue的形式提出");
    }
}
