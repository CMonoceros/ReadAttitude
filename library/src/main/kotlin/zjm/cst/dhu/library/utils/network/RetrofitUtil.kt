package zjm.cst.dhu.library.utils.network

import android.text.TextUtils
import android.util.Log
import com.google.gson.GsonBuilder
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.MyApplication
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by zjm on 2017/5/10.
 */

object RetrofitUtil {
    private val TAG = "RetrofitUtil"

    private fun setupLoggingBaseInterceptor():
            Interceptor = Interceptor { chain ->
        val request = chain.request()
        val requestTime = System.nanoTime()
        Log.i(TAG, "Sending request ${request.url()} on ${chain.connection()}\n${request.headers()}")
        val response = chain.proceed(request)
        val responseTime = System.nanoTime()
        Log.i(TAG, String.format(Locale.getDefault(),
                "Received response for ${response.request().url()} " +
                        "in %.1fms\n${response.headers()}", (responseTime - requestTime) / 1e6))
        response
    }

    private fun setupLoggingJsonInterceptor():
            HttpLoggingInterceptor {
        val mLoggingJsonInterceptor = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message ->
                    if (TextUtils.isEmpty(message)) return@Logger
                    val s = message.substring(0, 1)
                    //如果收到响应是json才打印
                    if ("{" == s || "[" == s) {
                        Log.i(TAG, message)
                    }
                })
        mLoggingJsonInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return mLoggingJsonInterceptor
    }


    fun setupOkHttpClient():
            OkHttpClient {
        val cache = Cache(File(MyApplication.context!!.cacheDir, "HttpCache"),
                (Constant.CACHE_SIZE).toLong())
        val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(Constant.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(Constant.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(Constant.WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(CacheInterceptor())
                .addInterceptor(setupLoggingBaseInterceptor())
                .addInterceptor(setupLoggingJsonInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
                .build()
        return okHttpClient
    }

    fun setupRetrofit(client: OkHttpClient, url: String):
            Retrofit {
        val gsonConverterFactory = GsonConverterFactory.create()

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
        return retrofit
    }
}
