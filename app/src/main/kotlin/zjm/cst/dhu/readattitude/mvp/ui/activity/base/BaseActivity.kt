package zjm.cst.dhu.readattitude.mvp.ui.activity.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import zjm.cst.dhu.readattitude.inject.component.ActivityComponent
import zjm.cst.dhu.readattitude.inject.module.ActivityModule
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.readattitude.inject.component.DaggerActivityComponent

/**
 * Created by zjm on 2017/5/9.
 */

abstract class BaseActivity<T : BasePresenter<*>>
    : AppCompatActivity() {

    protected var myPresenter: T? = null
    protected var myActivityComponent: ActivityComponent? = null
    private var firstBackPressTime: Long = 0

    abstract fun dataBinding()

    abstract fun setupInjector()

    abstract fun setupViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent()
        dataBinding()
        setupInjector()
        setupViews()
    }

    private fun setupActivityComponent() {
        myActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent((application as MyApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        val refWatcher = MyApplication.getRefWatcher(this)
        refWatcher.watch(this)
        if (myPresenter != null) {
            myPresenter!!.detachView()
        }
    }

    override fun onBackPressed() {
        val secondBackPressTime = System.currentTimeMillis()
        if (secondBackPressTime - firstBackPressTime > Constant.BACK_PRESS_TIME) {
            Toast.makeText(this, "Press the exit procedure again", Toast.LENGTH_SHORT).show()
            firstBackPressTime = secondBackPressTime
        } else {
            System.exit(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu):
            Boolean = super.onCreateOptionsMenu(menu)

    override fun onOptionsItemSelected(item: MenuItem):
            Boolean = super.onOptionsItemSelected(item)
}
