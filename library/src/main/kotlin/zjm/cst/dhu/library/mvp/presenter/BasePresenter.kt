package zjm.cst.dhu.library.mvp.presenter

import zjm.cst.dhu.library.mvp.view.base.BaseView

/**
 * Created by zjm on 2017/5/9.
 */

interface BasePresenter<in T : BaseView> {

    fun attachView(BaseView: T)

    fun detachView()
}
