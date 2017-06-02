package zjm.cst.dhu.library.utils.network

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.telephony.TelephonyManager

/**
 * Created by zjm on 2017/5/10.
 */

object NetworkUtil {
    /**
     * 判断是否有网络连接

     * @param context
     * *
     * @return
     */
    fun isNetworkConnected(context: Context?):
            Boolean {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取NetworkInfo对象
            val networkInfo = manager.activeNetworkInfo
            //判断NetworkInfo对象是否为空
            if (networkInfo != null)
                return networkInfo.isAvailable
        }
        return false
    }

    /**
     * 判断WIFI网络是否可用

     * @param context
     * *
     * @param context
     * *
     * @return
     */
    fun isMobileConnected(context: Context?):
            Boolean {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //获取NetworkInfo对象
            val networkInfo = manager.activeNetworkInfo
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable
        }
        return false
    }

    /**
     * 获取当前网络连接的类型信息
     * 原生

     * @param context
     * *
     * @return
     */
    fun getConnectedType(context: Context?):
            Int {
        if (context != null) {
            //获取手机所有连接管理对象
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //获取NetworkInfo对象
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isAvailable) {
                //返回NetworkInfo的类型
                return networkInfo.type
            }
        }
        return -1
    }

    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     * 自定义

     * @param context
     * *
     * @return
     */
    fun getAPNType(context: Context?):
            Int {
        //结果返回值
        var netType = 0
        if (context != null) {
            //获取手机所有连接管理对象
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //获取NetworkInfo对象
            //NetworkInfo对象为空 则代表没有网络
            val networkInfo = manager.activeNetworkInfo ?: return netType

            //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
            val nType = networkInfo.type
            if (nType == ConnectivityManager.TYPE_WIFI) {
                //WIFI
                netType = 1
            } else if (nType == ConnectivityManager.TYPE_MOBILE) {
                val nSubType = networkInfo.subtype
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming) {
                    netType = 4
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                        || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        && !telephonyManager.isNetworkRoaming) {
                    netType = 3
                    //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                        || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                        || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                        && !telephonyManager.isNetworkRoaming) {
                    netType = 2
                } else {
                    netType = 2
                }
            }
        }
        return netType
    }

    /**
     * 判断GPS是否打开
     * ACCESS_FINE_LOCATION权限

     * @param context
     * *
     * @return
     */
    fun isGPSEnabled(context: Context?):
            Boolean {
        var res = false;
        if (context != null) {
            //获取手机所有连接LOCATION_SERVICE对象
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            res = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
        return res
    }
}
