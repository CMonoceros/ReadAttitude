package zjm.cst.dhu.newsmodule.inject.module

import android.app.Activity
import android.content.Context

import dagger.Module
import dagger.Provides
import zjm.cst.dhu.library.inject.scope.PerActivity
import zjm.cst.dhu.library.inject.scope.PerContext

/**
 * Created by zjm on 2017/5/9.
 */

@Module
class ActivityModule(private val myActivity: Activity) {

    @Provides
    @PerActivity
    @PerContext("Activity")
    fun provideActivityContext():
            Context = myActivity

    @Provides
    @PerActivity
    fun provideActivity():
            Activity = myActivity
}
