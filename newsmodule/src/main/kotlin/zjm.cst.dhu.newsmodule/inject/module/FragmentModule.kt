package zjm.cst.dhu.newsmodule.inject.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

import dagger.Module
import dagger.Provides
import zjm.cst.dhu.library.inject.scope.PerContext
import zjm.cst.dhu.library.inject.scope.PerFragment

/**
 * Created by zjm on 2017/5/10.
 */
@Module
class FragmentModule(private val myFragment: Fragment) {

    @Provides
    @PerFragment
    @PerContext("Activity")
    fun provideActivityContext():
            Context = myFragment.activity


    @Provides
    @PerFragment
    fun provideActivity():
            Activity = myFragment.activity


    @Provides
    @PerFragment
    fun provideFragment():
            Fragment = myFragment

}
