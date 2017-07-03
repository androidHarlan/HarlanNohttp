/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.harlan.lhc.harlannohttp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import com.harlan.lhc.harlannohttp.activity.cache.CacheActivity;
import com.harlan.lhc.harlannohttp.activity.cancel.CancelActivity;
import com.harlan.lhc.harlannohttp.activity.define.DefineRequestActivity;
import com.harlan.lhc.harlannohttp.activity.download.DownloadActivity;
import com.harlan.lhc.harlannohttp.activity.json.JsonActivity;
import com.harlan.lhc.harlannohttp.activity.upload.UploadFileActivity;
import com.harlan.lhc.harlannohttp.adapter.RecyclerListMultiAdapter;
import com.harlan.lhc.harlannohttp.entity.ListItem;
import com.harlan.lhc.harlannohttp.util.DisplayUtils;
import com.harlan.lhc.harlannohttp.util.OnItemClickListener;
import com.harlan.lhc.harlannohttp.view.ListRecyclerCardItemDecoration;
import com.harlan.lhc.harlannohttp.R;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>开始界面.</p>
 * Created in Oct 21, 2015 2:19:16 PM.
 *
 * @author Yan Zhenjie.
 */
public class MainActivity extends AppCompatActivity {

    AppBarLayout mAppBarLayout;
    ViewGroup mToolbarRoot;
    private int headViewSize;

    ImageView mIvHeadBackground;
    ImageView mIvToolbarHead;
    RecyclerView mRvContent;

    String[] titles;
    String[] titlesDes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtils.initScreen(this);
        headViewSize = DisplayUtils.dip2px(42);

        setContentView(R.layout.activity_main);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbarRoot = (ViewGroup) findViewById(R.id.toolbar_root);
        mIvHeadBackground = (ImageView) findViewById(R.id.iv_head_background);
        mIvToolbarHead = (ImageView) findViewById(R.id.iv_toolbar_head);
        mRvContent = (RecyclerView) findViewById(R.id.rv_start_activity);

        titles = getResources().getStringArray(R.array.activity_start_items);
        titlesDes = getResources().getStringArray(R.array.activity_start_items_des);

        mIvHeadBackground.getLayoutParams().height = DisplayUtils.screenWidth * 12 / 13;
        mIvHeadBackground.requestLayout();

        // 让toolbar下来。
        boolean isHigh = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        if (isHigh) {
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        }
        ((ViewGroup.MarginLayoutParams) mToolbarRoot.getLayoutParams()).setMargins(-headViewSize, isHigh ?
                DisplayUtils.statusBarHeight : 0, 0, 0);

        mAppBarLayout.addOnOffsetChangedListener(offsetChangedListener);

        initialize();
    }

    /**
     * AppBarLayout的offset监听。
     */
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            mIvToolbarHead.setAlpha(percentage);

            int background = (int) (250 * percentage);
            mToolbarRoot.getBackground().mutate().setAlpha(background);

            int realSize = (int) (headViewSize * percentage);
            mToolbarRoot.setTranslationX(realSize);
        }
    };


    /**
     * 初始化页面功能。
     */
    private void initialize() {
        List<ListItem> listItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            listItems.add(new ListItem(titles[i], titlesDes[i]));
        }

        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new ListRecyclerCardItemDecoration());
        mRvContent.setNestedScrollingEnabled(true);

        RecyclerListMultiAdapter listAdapter = new RecyclerListMultiAdapter(listItems, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                goItemPager(position);
            }

        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_start_activity);
        recyclerView.setAdapter(listAdapter);
    }

    private void goItemPager(int position) {
        Intent intent = null;
        switch (position) {
            case 0:// 最原始使用方法
                intent = new Intent(this, OriginalActivity.class);
                break;
            case 1:// 各种请求方法演示(GET, POST, HEAD, PUT等等)
                intent = new Intent(this, MethodActivity.class);
                break;
            case 2:// 请求图片
                intent = new Intent(this, ImageActivity.class);
                break;
            case 3:// JsonObject, JsonArray
                intent = new Intent(this, JsonActivity.class);
                break;
            case 4:// POST一段JSON、XML，自定义包体等
                intent = new Intent(this, PostBodyActivity.class);
                break;
            case 5:// 自定义请求FastJson
                intent = new Intent(this, DefineRequestActivity.class);
                break;
            case 6:// NoHttp缓存演示
                intent = new Intent(this, CacheActivity.class);
                break;
            case 7:// 响应码302/303重定向演示
                intent = new Intent(this, RedirectActivity.class);
                break;
            case 8:// 文件上传
                intent = new Intent(this, UploadFileActivity.class);
                break;
            case 9: // 文件下载
                intent = new Intent(this, DownloadActivity.class);
                break;
            case 10:// 如何取消请求
                intent = new Intent(this, CancelActivity.class);
                break;
            case 11:// 同步请求
                intent = new Intent(this, SyncActivity.class);
                break;
            case 12:// 通过代理服务器请求
                intent = new Intent(this, ProXYActivity.class);
                break;
            case 13:// https请求
                intent = new Intent(this, HttpsActivity.class);
                break;
            default:
                break;
        }
        if (intent != null)
            startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
