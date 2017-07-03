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
package com.harlan.lhc.harlannohttp.activity.cache;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.harlan.lhc.harlannohttp.R;
import com.harlan.lhc.harlannohttp.activity.BaseActivity;
import com.harlan.lhc.harlannohttp.adapter.RecyclerListMultiAdapter;
import com.harlan.lhc.harlannohttp.entity.ListItem;
import com.harlan.lhc.harlannohttp.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan Zhenjie on 2016/3/13.
 *
 * @author Yan Zhenjie.
 */
public class CacheActivity extends BaseActivity {

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cache);

        List<ListItem> listItems = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.activity_cache_entrance);
        String[] titlesDes = getResources().getStringArray(R.array.activity_cache_entrance_des);
        for (int i = 0; i < titles.length; i++) {
            listItems.add(new ListItem(titles[i], titlesDes[i]));
        }

        RecyclerListMultiAdapter listAdapter = new RecyclerListMultiAdapter(listItems, mItemClickListener);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_cache_activity);
        recyclerView.setAdapter(listAdapter);
    }

    /**
     * list item单击。
     */
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = null;
            switch (position) {
                case 0:// Http标准协议的缓存。
                    intent = new Intent(CacheActivity.this, CacheHttpActivity.class);
                    break;
                case 1:// 请求网络失败后返回上次的缓存。
                    intent = new Intent(CacheActivity.this, CacheRequestFailedReadCacheActivity.class);
                    break;
                case 2:// 没有缓存才去请求网络。
                    intent = new Intent(CacheActivity.this, CacheNoneCacheRequestNetWorkActivity.class);
                    break;
                case 3:// 仅仅请求缓存。
                    intent = new Intent(CacheActivity.this, CacheOnlyReadCacheActivity.class);
                    break;
                case 4:// 仅仅请求网络。
                    intent = new Intent(CacheActivity.this, CacheOnlyRequestNetworkActivity.class);
                    break;
                default:
                    break;
            }
            if (intent != null)
                startActivity(intent);
        }
    };
}
