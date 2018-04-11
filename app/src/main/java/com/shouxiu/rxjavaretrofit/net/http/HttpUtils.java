package com.shouxiu.rxjavaretrofit.net.http;

import android.content.Context;

import com.shouxiu.rxjavaretrofit.net.config.NetWorkConfiguration;
import com.shouxiu.rxjavaretrofit.net.cookie.SimpleCookieJar;
import com.shouxiu.rxjavaretrofit.net.interceptor.LogInterceptor;
import com.shouxiu.rxjavaretrofit.net.request.RetrofitClient;
import com.shouxiu.rxjavaretrofit.utils.LogUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author yeping
 * @date 2018/3/31 11:24
 * 对OkHttpClient进行配置
 */

public class HttpUtils {
    //    获得HttpUtils实例
    private static HttpUtils mInstance;
    //    OkHttpClient对象
    private OkHttpClient mOkHttpClient;
    //    网络参数
    private static NetWorkConfiguration configuration;
    private Context context;


    public HttpUtils(Context context) {
        //创建默认 okHttpClient对象
        this.context = context;
        /**进行默认配置
         *    未配置configuration ,
         *
         */
        if (configuration == null) {
            configuration = new NetWorkConfiguration(context);
        }
        if (configuration.getIsCache()) {
            mOkHttpClient = new OkHttpClient.Builder()
                    //                   网络缓存拦截器
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(interceptor)
                    //                    自定义网络Log显示
                    .addInterceptor(new LogInterceptor())
                    .cache(configuration.getDiskCache())
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LogInterceptor())
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();

        }

        /**
         *
         *  判断是否在AppLication中配置Https证书
         *
         */
        if (configuration.getCertificates() != null) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .sslSocketFactory(HttpsUtils.getSslSocketFactory(configuration.getCertificates(), null, null))
                    .build();
        }
    }

    /**
     * 获得OkHttpClient实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 设置HTTPS客户端带证书访问
     *
     * @param certificates 本地证书
     */
    public HttpUtils setCertificates(InputStream... certificates) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
        return this;
    }

    /**
     * 设置是否打印网络日志
     *
     * @param falg
     */
    public HttpUtils setDBugLog(boolean falg) {
        if (falg) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return this;
    }

    /**
     * 设置Coolie
     *
     * @return
     */
    public HttpUtils addCookie() {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .cookieJar(new SimpleCookieJar())
                .build();
        return this;
    }

    /**
     * ---> 针对无网络情况
     * 是否加载本地缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadDiskCache(boolean isCache) {
        configuration.isCache(isCache);
        configuration.isDiskCache(isCache);
        return this;
    }

    /**
     * 是否加载内存缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadMemoryCache(boolean isCache) {
        configuration.isCache(isCache);
        configuration.isMemoryCache(isCache);
        return this;
    }

    public RetrofitClient getRetrofitClient() {

        LogUtil.i("configuration:" + configuration.toString());
        return new RetrofitClient(configuration.getBaseUrl(), mOkHttpClient);
    }

    /**
     * 网络拦截器
     * 进行网络操作的时候进行拦截
     */
    final Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //断网后是否加载本地缓存数据
            if (!NetworkUtil.isNetworkAvailable(configuration.context) && configuration.getIsDiskCache()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            } else if (!NetworkUtil.isNetworkAvailable(configuration.context) && configuration.getIsMemoryCache()) {
                //加载内存缓存数据
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            } else {
                //加载网络数据
                request = request.newBuilder()
//                        .addHeader("Content-Type","application/json")
//                        .addHeader("charset","UTF-8")
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            }
            Response response = chain.proceed(request);
            //有网进行内存缓存数据，  需要服务器支持缓存，开启Iscache报错
            if (NetworkUtil.isNetworkAvailable(configuration.context) && configuration.getIsMemoryCache()) {
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + configuration.getmemoryCacheTime())
                        .removeHeader("Pragma")
                        .build();
            } else {
                //              进行本地缓存数据
                if (configuration.getIsDiskCache()) {
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + configuration.getDiskCacheTime())
                            .removeHeader("Pragma")
                            .build();
                }
            }
            return response;
        }
    };

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static HttpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils(context);
                }
            }
        }
        return mInstance;
    }
}
