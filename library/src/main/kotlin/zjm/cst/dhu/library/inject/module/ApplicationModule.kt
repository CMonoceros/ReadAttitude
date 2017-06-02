package zjm.cst.dhu.library.inject.module

import android.content.Context
import dagger.Module
import dagger.Provides
import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.inject.scope.PerApplication
import zjm.cst.dhu.library.inject.scope.PerContext

/**
 * Created by zjm on 2017/5/9.
 */
@Module
class ApplicationModule(private val myApplication: MyApplication) {

    @Provides
    @PerApplication
    @PerContext("Application")
    fun provideMyApplicationContext():
            Context = myApplication.applicationContext
}
