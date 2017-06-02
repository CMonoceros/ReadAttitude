package zjm.cst.dhu.newsmodule.mvp.view


import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.library.mvp.view.base.BaseView

/**
 * Created by zjm on 2017/5/10.
 */

interface NewsBarContract {
    interface View
        : BaseView {
        fun loadChannelSuccess(list: List<NewsChannel>?)

        fun loadChannelError()

        fun showSnackbarMessage(message: String)

        fun uploadCrashLog(log: String)
    }

    interface Presenter
        : BasePresenter<View> {
        fun loadChannel()

        fun readCrashLog()
    }
}
