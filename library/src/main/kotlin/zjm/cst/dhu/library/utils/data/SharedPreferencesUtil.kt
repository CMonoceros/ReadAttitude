package zjm.cst.dhu.library.utils.data

import android.content.Context
import android.content.SharedPreferences
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.MyApplication

/**
 * Created by zjm on 2017/5/9.
 */

object SharedPreferencesUtil {

    val sharedPreferences: SharedPreferences
        get() = MyApplication.Companion.context!!.
                getSharedPreferences(Constant.SHARED_READ_ATTITUDE, Context.MODE_PRIVATE)

    var isFirstTime: Boolean
        get() = SharedPreferencesUtil.sharedPreferences.getBoolean(Constant.SHARED_FIRST_TIME, true)
        set(firstTime) {
            val editor = SharedPreferencesUtil.sharedPreferences.edit()
            editor.putBoolean(Constant.SHARED_FIRST_TIME, firstTime)
            editor.apply()
        }
}
