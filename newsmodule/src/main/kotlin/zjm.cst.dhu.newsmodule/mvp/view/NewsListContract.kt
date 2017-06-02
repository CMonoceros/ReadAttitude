package zjm.cst.dhu.newsmodule.mvp.view


import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.library.mvp.view.base.BaseView

/**
 * Created by zjm on 2017/5/10.
 */

interface NewsListContract {
    interface View
        : BaseView {
        fun loadNewListSuccess(list: List<NewsData>)

        fun loadNewListError()

        fun loadNextListSuccess(list: List<NewsData>)

        fun loadNextListError()

        fun floatingActionButtonClick()
    }

    interface Presenter
        : BasePresenter<View> {

        fun setValue(id: String, type: String)

        fun loadNewList()

        fun loadNextList()
    }
}
