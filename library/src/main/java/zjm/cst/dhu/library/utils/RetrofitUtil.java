package zjm.cst.dhu.library.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.inject.scope.PerApplication;
import zjm.cst.dhu.library.inject.scope.PerFragment;

/**
 * Created by zjm on 2017/5/10.
 */

public class RetrofitUtil {


    public static OkHttpClient setupOkHttpClient() {
        Interceptor mLoggingBaseInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long requestTime = System.nanoTime();
                Log.i("Request", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
                Response response = chain.proceed(request);
                long responseTime = System.nanoTime();
                Log.i("Response", String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                        response.request().url(), (responseTime - requestTime) / 1e6d, response.headers()));
                return response;
            }
        };

        HttpLoggingInterceptor mLoggingJsonInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {

                    @Override
                    public void log(String message) {
                        if (TextUtils.isEmpty(message)) return;
                        String s = message.substring(0, 1);
                        //如果收到响应是json才打印
                        if ("{".equals(s) || "[".equals(s)) {
                            long responseTime = System.nanoTime();
                            Log.i("Response Json", message);
                        }
                    }
                });
        mLoggingJsonInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(new File(MyApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .addInterceptor(mLoggingBaseInterceptor)
                .addInterceptor(mLoggingJsonInterceptor)
                .addNetworkInterceptor(new CacheInterceptor())
                .build();
        return okHttpClient;
    }

    public static Retrofit setupRetrofit(OkHttpClient client, String url) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
