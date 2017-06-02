package zjm.cst.dhu.newsmodule.mvp.ui.fragment.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.leakcanary.RefWatcher

import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.newsmodule.inject.component.DaggerFragmentComponent
import zjm.cst.dhu.newsmodule.inject.component.FragmentComponent
import zjm.cst.dhu.newsmodule.inject.module.FragmentModule

/**
 * Created by zjm on 2017/5/10.
 */

abstract class BaseFragment<T : BasePresenter<*>>
    : Fragment() {
    protected var myPresenter: T? = null
    protected var myFragmentComponent: FragmentComponent?=null

    abstract fun dataBinding(rootView: View)

    abstract fun setupInjector()

    abstract fun setupViews(rootView: View)

    abstract val fragmentId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
        setupInjector()
    }

    private fun setupFragmentComponent() {
        myFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent((activity.application as MyApplication).applicationComponent)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        val refWatcher = MyApplication.getRefWatcher(activity)
        refWatcher.watch(this)
        if (myPresenter != null) {
            myPresenter!!.detachView()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        val rootView = inflater!!.inflate(fragmentId, container, false)
        dataBinding(rootView)
        setupViews(rootView)
        return rootView
    }
}
