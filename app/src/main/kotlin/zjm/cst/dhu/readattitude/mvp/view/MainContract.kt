package zjm.cst.dhu.readattitude.mvp.view

import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.library.mvp.view.base.BaseView

/**
 * Created by zjm on 2017/5/9.
 */

interface MainContract {
    interface View
        : BaseView

    interface Presenter
        : BasePresenter<View>
}
