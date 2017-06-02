package zjm.cst.dhu.library.utils.others

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import android.os.Environment
import android.util.Log
import zjm.cst.dhu.library.Constant
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zjm on 2017/2/23.
 */

/**
 * 处理程序中未捕获的异常，将异常写入日志文件
 */
class CrashHandler private constructor(private val mContext: Context)
    : Thread.UncaughtExceptionHandler {

    private val mDefaultHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()

    private val infos = HashMap<String, String>()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        ex.printStackTrace()
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Log.e(TAG, e.message)
            }

            System.exit(0)
        }
    }

    private fun handleException(ex: Throwable?):
            Boolean {
        // 如果是调试状态则不生成异常文件，让系统默认的异常处理器来处理
        if (Debug.isDebuggerConnected())
            return false
        if (ex == null)
            return false
        // 收集设备参数信息
        collectDeviceInfo(mContext)
        // 保存日志文件
        saveCrashInfo2File(ex)
        return true
    }

    private fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString()
                infos.put("versionName", versionName)
                infos.put("versionCode", versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occurred when collect package info", e)
        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos.put(field.name, field.get(null).toString())
            } catch (e: Exception) {
                Log.e(TAG, "an error occurred when collect crash info", e)
            }

        }
    }

    private fun saveCrashInfo2File(ex: Throwable) {
        val sb = StringBuffer()
        for ((key, value) in infos) {
            sb.append(key + "=" + value + "\n")
        }
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        val df = SimpleDateFormat("yyyyMMddHHmmssSSS")
        try {
            val fileName = String.format("crash-%s.log", df.format(Date(System.currentTimeMillis())))
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                val path = Constant.CRASH_LOG_FILE_PATH
                val dir = File(path)
                if (!dir.exists())
                    dir.mkdirs()
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
        } catch (e: Exception) {
            Log.e(TAG, "an error occured while writing file...", e)
        }
    }

    companion object {
        private val TAG = CrashHandler::class.java.simpleName

        private var instance: CrashHandler? = null

        fun getInstance(context: Context): CrashHandler {
            if (instance == null) {
                synchronized(CrashHandler::class.java) {
                    if (instance == null) {
                        instance = CrashHandler(context)
                    }
                }
            }
            return instance!!
        }
    }
}
