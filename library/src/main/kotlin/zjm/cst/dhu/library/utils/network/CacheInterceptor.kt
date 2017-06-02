package zjm.cst.dhu.library.utils.network

import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.utils.network.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by zjm on 2017/5/10.
 */

class CacheInterceptor
    : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain):
            Response {
        var request = chain.request()
        if (!NetworkUtil.isNetworkConnected(MyApplication.context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }
        val response = chain.proceed(request)
        if (NetworkUtil.isNetworkConnected(MyApplication.context)) {// 有网络时 设置缓存超时时间0个小时
            val maxAge = Constant.CACHE_NETWORK
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
        } else { // 无网络时
            val maxStale = Constant.CACHE_NONETWORK
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build()
        }
        return response
    }
}
