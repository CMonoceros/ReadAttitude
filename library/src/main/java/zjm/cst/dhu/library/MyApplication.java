package zjm.cst.dhu.library;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.query.QueryBuilder;

import zjm.cst.dhu.library.inject.component.ApplicationComponent;
import zjm.cst.dhu.library.inject.component.DaggerApplicationComponent;
import zjm.cst.dhu.library.inject.module.ApplicationModule;
import zjm.cst.dhu.library.mvp.model.DaoMaster;
import zjm.cst.dhu.library.mvp.model.DaoSession;
import zjm.cst.dhu.library.mvp.model.NewsChannelDao;
import zjm.cst.dhu.library.utils.CrashHandler;

/**
 * Created by zjm on 2017/5/9.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private RefWatcher mRefWatcher;
    private ApplicationComponent mApplicationComponent;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        setCrashHandler();
        setupLeakCanary();
        setupStrictMode();
        setupDatabase();
        setupComponent();
    }

    private void setCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }


    private void setupLeakCanary() {
        mRefWatcher = LeakCanary.install(this);
    }

    private void setupStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog() // 在logcat中打印违规异常信息
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constant.DATABASE_NAME, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();

        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }

    private void setupComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getContext() {
        return mContext;
    }

    public static NewsChannelDao getNewChannelDao() {
        return mDaoSession.getNewsChannelDao();
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        return myApplication.mRefWatcher;
    }
}
