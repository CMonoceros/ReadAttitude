package zjm.cst.dhu.library

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import zjm.cst.dhu.library.inject.component.ApplicationComponent
import zjm.cst.dhu.library.inject.component.DaggerApplicationComponent
import zjm.cst.dhu.library.inject.module.ApplicationModule
import zjm.cst.dhu.library.mvp.model.DaoMaster
import zjm.cst.dhu.library.mvp.model.DaoSession
import zjm.cst.dhu.library.mvp.model.NewsChannelDao
import zjm.cst.dhu.library.utils.others.CrashHandler
import org.greenrobot.greendao.query.QueryBuilder
import zjm.cst.dhu.library.BuildConfig

/**
 * Created by zjm on 2017/5/9.
 */

class MyApplication
    : Application() {
    private var mRefWatcher: RefWatcher? = null
    var applicationComponent: ApplicationComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        context = this
        setCrashHandler()
        setupLeakCanary()
        setupStrictMode()
        setupDatabase()
        setupComponent()
    }

    private fun setCrashHandler() {
        val crashHandler = CrashHandler.getInstance(this)
        Thread.setDefaultUncaughtExceptionHandler(crashHandler)
    }


    private fun setupLeakCanary() {
        mRefWatcher = LeakCanary.install(this)
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog() // 在logcat中打印违规异常信息
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private fun setupDatabase() {
        val devOpenHelper = DaoMaster.DevOpenHelper(this, Constant.DATABASE_NAME, null)
        val daoMaster = DaoMaster(devOpenHelper.getEncryptedWritableDb(Constant.DATABASE_PASSWORD))
        mDaoSession = daoMaster.newSession()

        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG
    }

    private fun setupComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {

        var context: Context? = null
            private set
        private var mDaoSession: DaoSession? = null

        val newChannelDao: NewsChannelDao
            get() = mDaoSession!!.newsChannelDao

        fun getRefWatcher(context: Context)
                : RefWatcher = (context.applicationContext as MyApplication).mRefWatcher!!
    }
}
