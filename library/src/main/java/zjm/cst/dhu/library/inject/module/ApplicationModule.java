package zjm.cst.dhu.library.inject.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.inject.scope.PerApplication;
import zjm.cst.dhu.library.inject.scope.PerContext;

/**
 * Created by zjm on 2017/5/9.
 */
@Module
public class ApplicationModule {
    private MyApplication myApplication;

    public ApplicationModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @PerApplication
    @PerContext("Application")
    public Context provideMyApplicationContext() {
        return myApplication.getApplicationContext();
    }
}
