package com.shouxiu.rxjavaretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.api.HomeApi;
import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.api.ParamsMapUtils;
import com.shouxiu.rxjavaretrofit.net.cache.XCCacheManager;
import com.shouxiu.rxjavaretrofit.net.callback.RxSubscriber;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.net.http.HttpUtils;
import com.shouxiu.rxjavaretrofit.net.transformer.DefaultTransformer;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;

import java.util.List;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCateList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_home_info = findViewById(R.id.tv_home_info);
        HttpUtils.getInstance(this)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .compose(new DefaultTransformer<List<HomeCateList>>())  //预处理错误信息
                .subscribe(new RxSubscriber<List<HomeCateList>>() {     //订阅者响应类

                    @Override
                    protected String prepareCache() {
                        if (!NetworkUtil.isNetworkAvailable(MainActivity.this)) {
                            return XCCacheManager.getInstance(MainActivity.this).readCache(getHomeCateList);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(List<HomeCateList> homeCateLists) {
                        String text = GsonUtil.object2Json(homeCateLists);
                        XCCacheManager.getInstance(MainActivity.this).writeCache(getHomeCateList, text);
                        tv_home_info.setText(text);
                    }

                    @Override
                    public void onFail(ResponseThrowable t) {
                        Toast.makeText(MainActivity.this, t.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
