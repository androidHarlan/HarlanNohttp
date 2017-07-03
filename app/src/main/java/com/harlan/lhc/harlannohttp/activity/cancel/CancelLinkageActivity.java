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
package com.harlan.lhc.harlannohttp.activity.cancel;

import android.os.Bundle;

import com.harlan.lhc.harlannohttp.R;
import com.harlan.lhc.harlannohttp.activity.BaseActivity;
import com.harlan.lhc.harlannohttp.util.Constants;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;


/**
 * <p>和Activity声明周期联动取消。</p>
 * Created on 2016/5/31.
 *
 * @author Yan Zhenjie;
 */
public class CancelLinkageActivity extends BaseActivity {

    /**
     * 请求对象。
     */
    private Request<String> mRequest;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cacel_demo);

        mRequest = new StringRequest(Constants.URL_NOHTTP_JSONOBJECT, RequestMethod.GET);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // 退出时取消请求。
        if (mRequest != null)
            mRequest.cancel();
    }

}
