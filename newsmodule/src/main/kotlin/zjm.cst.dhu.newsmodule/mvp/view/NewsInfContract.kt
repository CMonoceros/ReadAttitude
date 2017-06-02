package zjm.cst.dhu.newsmodule.mvp.view


import zjm.cst.dhu.library.mvp.model.NewsInf
import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.library.mvp.view.base.BaseView

/**
 * Created by zjm on 2017/5/14.
 */

interface NewsInfContract {
    interface View
        : BaseView {
        fun loadNewsInfSuccess(newsInf: NewsInf?)

        fun loadNewsInfError()
    }

    interface Presenter
        : BasePresenter<View> {

        fun loadNewsInf(postId: String)
    }
}
