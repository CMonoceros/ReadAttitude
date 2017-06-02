package zjm.cst.dhu.newsmodule.inject.component

import android.app.Activity
import android.content.Context

import dagger.Component
import zjm.cst.dhu.library.inject.component.ApplicationComponent
import zjm.cst.dhu.library.inject.scope.PerContext
import zjm.cst.dhu.library.inject.scope.PerFragment
import zjm.cst.dhu.newsmodule.inject.module.FragmentModule
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.ChannelManageFragment
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsBarFragment
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsInfFragment
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsListFragment

/**
 * Created by zjm on 2017/5/10.
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    @get:PerContext("Activity")
    val activityContext: Context

    @get:PerContext("Application")
    val applicationContext: Context

    val activity: Activity

    fun inject(newsBarFragment: NewsBarFragment)

    fun inject(newsListFragment: NewsListFragment)

    fun inject(channelManageFragment: ChannelManageFragment)

    fun inject(newsInfFragment: NewsInfFragment)
}
