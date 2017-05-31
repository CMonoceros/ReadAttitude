package zjm.cst.dhu.newsmodule.inject.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import zjm.cst.dhu.library.inject.scope.PerActivity;
import zjm.cst.dhu.library.inject.scope.PerContext;

/**
 * Created by zjm on 2017/5/9.
 */

@Module
public class ActivityModule {
    private Activity myActivity;

    public ActivityModule(Activity myActivity) {
        this.myActivity = myActivity;
    }

    @Provides
    @PerActivity
    @PerContext("Activity")
    public Context provideActivityContext() {
        return myActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return myActivity;
    }
}
