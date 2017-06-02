package zjm.cst.dhu.readattitude.inject.component

import android.app.Activity
import android.content.Context

import dagger.Component
import zjm.cst.dhu.readattitude.inject.module.ActivityModule
import zjm.cst.dhu.readattitude.mvp.ui.activity.MainActivity
import zjm.cst.dhu.library.inject.component.ApplicationComponent
import zjm.cst.dhu.library.inject.scope.PerActivity
import zjm.cst.dhu.library.inject.scope.PerContext

/**
 * Created by zjm on 2017/5/9.
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    @get:PerContext("Activity")
    val activityContext: Context

    @get:PerContext("Application")
    val applicationContext: Context

    val activity: Activity

    fun inject(activity: MainActivity)
}
